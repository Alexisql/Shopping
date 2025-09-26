package com.alexis.shopping.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexis.shopping.domain.model.Pokemon
import com.alexis.shopping.domain.repository.INotificationRepository
import com.alexis.shopping.domain.repository.IPokemonRepository
import com.alexis.shopping.ui.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: IPokemonRepository,
    private val dispatcher: CoroutineDispatcher,
    private val notification: INotificationRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Pokemon>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Pokemon>>> = _state

    init {
        getPokemonsCart()
    }

    private fun getPokemonsCart() {
        viewModelScope.launch {
            repository.getPokemonCart()
                .catch { _state.value = UiState.Failure(it) }
                .flowOn(dispatcher)
                .collect { pokemons ->
                    _state.value = UiState.Success(pokemons)
                }
        }
    }

    fun deletePokemonCart(pokemonId: Int) {
        viewModelScope.launch(dispatcher) {
            repository.removePokemon(pokemonId)
                .onSuccess { Log.i("CartViewModel", "Cart remove") }
                .onFailure { Log.e("CartViewModel", "Error remove pokemon to cart", it) }
        }
    }

    fun sendNotification(tittle: String, message: String) {
        viewModelScope.launch {
            notification.sendNotification(
                title = tittle,
                message = message
            )
        }
    }
}