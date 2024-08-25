package com.gomsoo.shaken.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gomsoo.shaken.core.data.repository.CocktailRepository
import com.gomsoo.shaken.core.extension.combine
import com.gomsoo.shaken.core.model.data.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private val keyword: MutableStateFlow<String> = MutableStateFlow("")

    fun setKeyword(keyword: String) {
        this.keyword.update { keyword }
    }

    private val searched: StateFlow<List<Cocktail>> = keyword.debounce(800.milliseconds)
        .filter(String::isNotBlank)
        .map(cocktailRepository::search)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

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
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SearchUiState.Initial)
}
