package com.alexis.shopping.domain.repository

import androidx.paging.PagingData
import com.alexis.shopping.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {
    fun getPokemonList(query: String = ""): Flow<PagingData<Pokemon>>
    suspend fun addPokemon(pokemon: Pokemon): Result<Unit>
    suspend fun removePokemon(pokemonId: Int) : Result<Unit>
    fun getPokemonCart(): Flow<List<Pokemon>>
}