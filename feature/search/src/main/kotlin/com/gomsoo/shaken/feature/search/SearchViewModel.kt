package com.gomsoo.shaken.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gomsoo.shaken.core.data.repository.CocktailRepository
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

    private val keyword: MutableStateFlow<String> = MutableStateFlow("Martini")

    fun setKeyword(keyword: String) {
        this.keyword.update { keyword }
    }

    /**
     * TODO Improvement
     *   - [ ] Loading state
     *   - [ ] Caching
     */
    val searchUiState: StateFlow<SearchUiState> = keyword.debounce(800.milliseconds)
        .filter(String::isNotBlank)
        .map(cocktailRepository::search)
        .map { if (it.isEmpty()) SearchUiState.Empty else SearchUiState.Success(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SearchUiState.Initial)
}
