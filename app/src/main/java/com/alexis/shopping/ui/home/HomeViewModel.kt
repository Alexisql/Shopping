package com.alexis.shopping.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexis.shopping.domain.model.Pokemon
import com.alexis.shopping.domain.repository.IPokemonRepository
import com.alexis.shopping.ui.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: IPokemonRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val state: StateFlow<UiState<Unit>> = _state

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val pokemons: Flow<PagingData<Pokemon>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getPokemonList()
                    .onEach { _state.value = UiState.Success(Unit) }
            } else {
                repository.searchPokemon(query)
            }
        }
        .catch {
            _state.value = UiState.Failure(it)
            emit(PagingData.empty())
        }
        .flowOn(dispatcher)
        .cachedIn(viewModelScope)

    fun searchPokemon(query: String) {
        _searchQuery.value = query
    }

    fun addToCart(pokemon: Pokemon) {
        viewModelScope.launch(dispatcher) {
            repository.addPokemon(pokemon)
                .onSuccess { Log.i("HomeViewModel", "Cart adding") }
                .onFailure { Log.e("HomeViewModel", "Error adding pokemon to cart", it) }
        }
    }
}