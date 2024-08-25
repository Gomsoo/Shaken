package com.gomsoo.shaken.feature.detail

import androidx.lifecycle.SavedStateHandle
import com.gomsoo.shaken.core.data.repository.CocktailRepository
import com.gomsoo.shaken.core.ui.BaseViewModel
import com.gomsoo.shaken.feature.detail.navigation.COCKTAIL_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cocktailRepository: CocktailRepository
) : BaseViewModel() {

    private val cocktailId: StateFlow<String?> = savedStateHandle
        .getStateFlow(COCKTAIL_ID_ARG, null)

    val detailUiState: StateFlow<DetailUiState> = cocktailId
        .mapLatest {
            if (it.isNullOrBlank()) {
                DetailUiState.InvalidId
            } else {
                cocktailRepository.getDetail(it)?.let(DetailUiState::Success)
                    ?: DetailUiState.InvalidResult
            }
        }
        .stateIn(DetailUiState.Initial)
}
