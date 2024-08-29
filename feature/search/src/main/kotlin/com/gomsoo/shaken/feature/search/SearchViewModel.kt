package com.gomsoo.shaken.feature.search

import androidx.lifecycle.viewModelScope
import com.gomsoo.shaken.core.data.repository.CocktailRepository
import com.gomsoo.shaken.core.extension.combine
import com.gomsoo.shaken.core.model.data.CocktailWithFavorite
import com.gomsoo.shaken.core.model.data.SimpleCocktailWithFavorite
import com.gomsoo.shaken.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : BaseViewModel() {

    private val keyword: MutableStateFlow<String> = MutableStateFlow("")

    fun setKeyword(keyword: String) {
        this.keyword.update { keyword }
    }

    private val favoriteCocktailIds: StateFlow<Set<String>> = cocktailRepository
        .getFavoriteCocktailIds()
        .stateIn(emptySet())

    fun setFavorite(item: SearchUiState.Item) {
        viewModelScope.launch {
            cocktailRepository.setFavorite(item.cocktail.id, !item.isFavorite)
        }
    }

    fun setFavorite(item: SimpleCocktailWithFavorite) {
        viewModelScope.launch {
            cocktailRepository.setFavorite(item)
        }
    }

    fun setFavorite(item: CocktailWithFavorite) {
        viewModelScope.launch {
            cocktailRepository.setFavorite(item)
        }
    }

    /**
     * 시작 '단어'로 검색이 불가능하여 한 글자 이상 입력한 경우 이름 검색으로 대체
     */
    private val searched: StateFlow<List<SearchUiState.Item>?> = keyword
        .debounce(800.milliseconds)
        .map { it.trim() }
        .flatMapLatest { keyword ->
            flowOf(
                when {
                    keyword.isBlank() -> cocktailRepository.getAlcoholics()
                    keyword.length == 1 -> cocktailRepository.searchStartWith(keyword)
                    else -> cocktailRepository.search(keyword)
                }
            )
                .combine(favoriteCocktailIds)
                .map { (cocktails, ids) ->
                    cocktails.map { cocktail ->
                        SearchUiState.Item(
                            cocktail = cocktail,
                            isFavorite = cocktail.id in ids,
                            keyword = keyword
                        )
                    }
                }
        }
        .stateIn(null)

    private val detailId = MutableStateFlow<String?>(null)

    fun setDetailId(id: String?) {
        detailId.update { id }
    }

    /**
     * TODO Caching cocktail detailed info
     */
    private val detail: StateFlow<CocktailWithFavorite?> = detailId
        .flatMapLatest {
            if (it.isNullOrBlank()) {
                flowOf(null)
            } else {
                favoriteCocktailIds.combine(flowOf(cocktailRepository.getDetail(it)))
                    .map { (favoriteIds, detail) ->
                        detail?.let { CocktailWithFavorite(detail, detail.id in favoriteIds) }
                    }
            }
        }
        .stateIn(null)

    /**
     * TODO Improvement
     *   - [x] Fix initial state skipped
     *   - [x] Loading state
     *   - [ ] Caching
     */
    val searchUiState: StateFlow<SearchUiState> = searched.combine(keyword, detail)
        .map { (searched, keyword, detail) ->
            when {
                searched == null -> SearchUiState.Initial
                searched.isEmpty() -> SearchUiState.Empty(keyword)
                else -> SearchUiState.Success(searched, keyword, detail)
            }
        }
        .stateIn(SearchUiState.Initial)
}
