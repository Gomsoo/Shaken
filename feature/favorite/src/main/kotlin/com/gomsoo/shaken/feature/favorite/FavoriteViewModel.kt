package com.gomsoo.shaken.feature.favorite

import com.gomsoo.shaken.core.data.repository.CocktailRepository
import com.gomsoo.shaken.core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    cocktailRepository: CocktailRepository
) : BaseViewModel() {

    val favoriteUiState: StateFlow<FavoriteUiState> = cocktailRepository.getFavoriteCocktails()
        .map {
            when {
                it.isEmpty() -> FavoriteUiState.Empty
                else -> FavoriteUiState.Success(it)
            }
        }
        .stateIn(FavoriteUiState.Initial)
}
