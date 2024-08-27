package com.gomsoo.shaken.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gomsoo.shaken.core.data.repository.CocktailRepository
import com.gomsoo.shaken.core.extension.combine
import com.gomsoo.shaken.core.model.data.CocktailWithFavorite
import com.gomsoo.shaken.core.ui.BaseViewModel
import com.gomsoo.shaken.feature.detail.navigation.COCKTAIL_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cocktailRepository: CocktailRepository
) : BaseViewModel() {

    private val favoriteCocktailIds: StateFlow<Set<String>> = cocktailRepository
        .getFavoriteCocktailIds()
        .stateIn(emptySet())

    fun setFavorite(cocktailWithFavorite: CocktailWithFavorite) {
        viewModelScope.launch {
            cocktailRepository.setFavorite(cocktailWithFavorite)
        }
    }

    private val cocktailId: StateFlow<String?> = savedStateHandle
        .getStateFlow(COCKTAIL_ID_ARG, null)

    val detailUiState: StateFlow<DetailUiState> = cocktailId.combine(favoriteCocktailIds)
        .mapLatest { (cocktailId, favoriteIds) ->
            if (cocktailId.isNullOrBlank()) {
                DetailUiState.InvalidId
            } else {
                cocktailRepository.getDetail(cocktailId)
                    ?.let { DetailUiState.Success(CocktailWithFavorite(it, it.id in favoriteIds)) }
                    ?: DetailUiState.InvalidResult
            }
        }
        .stateIn(DetailUiState.Initial)
}
