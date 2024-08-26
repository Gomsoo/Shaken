package com.gomsoo.shaken.feature.search

import com.gomsoo.shaken.core.data.repository.CocktailRepository
import com.gomsoo.shaken.core.extension.combine
import com.gomsoo.shaken.core.model.data.SimpleCocktail
import com.gomsoo.shaken.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
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

    /**
     * 시작 '단어'로 검색이 불가능하여 한 글자 이상 입력한 경우 이름 검색으로 대체
     */
    private val searched: StateFlow<List<SimpleCocktail>> = keyword.debounce(800.milliseconds)
        .map { it.trim() }
        .map { keyword ->
            when {
                keyword.isBlank() -> cocktailRepository.getAlcoholics()
                keyword.length == 1 -> cocktailRepository.searchStartWith(keyword)
                else -> cocktailRepository.search(keyword)
            }
        }
        .stateIn(emptyList())

    /**
     * TODO Improvement
     *   - [ ] Fix initial state skipped
     *   - [ ] Loading state
     *   - [ ] Caching
     */
    val searchUiState: StateFlow<SearchUiState> = searched.combine(keyword)
        .map { (searched, keyword) ->
            if (searched.isEmpty()) {
                SearchUiState.Empty(keyword)
            } else {
                SearchUiState.Success(searched, keyword)
            }
        }
        .stateIn(SearchUiState.Initial)
}
