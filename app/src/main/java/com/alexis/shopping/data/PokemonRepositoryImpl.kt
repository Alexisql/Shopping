package com.alexis.shopping.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.alexis.shopping.data.local.PokemonDataBase
import com.alexis.shopping.data.local.dao.PokemonDao
import com.alexis.shopping.data.local.entity.toDomain
import com.alexis.shopping.data.remote.service.PokemonService
import com.alexis.shopping.domain.model.Pokemon
import com.alexis.shopping.domain.model.toDomain
import com.alexis.shopping.domain.model.toEntity
import com.alexis.shopping.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonService,
    private val db: PokemonDataBase,
    private val pokemonDao: PokemonDao
) : IPokemonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemonList(query: String): Flow<PagingData<Pokemon>> {
        return try {
            Pager(
                config = PagingConfig(pageSize = 20),
                remoteMediator = PokemonMediator(api, db),
                pagingSourceFactory = { pokemonDao.getAllPokemon(query) }
            ).flow.map { pagingData ->
                pagingData.map { it.toDomain() }
            }
        } catch (exception: Exception) {
            Log.e("IPokemonRepository", "Error getting pokemons", exception)
            throw Exception("Error getting pokemons", exception)
        }
    }

    override suspend fun addPokemon(pokemon: Pokemon): Result<Unit> {
        return try {
            pokemonDao.insertCartItem(pokemon.toEntity())
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun removePokemon(pokemonId: Int): Result<Unit> {
        return try {
            pokemonDao.deleteCartItemById(pokemonId)
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override fun getPokemonCart(): Flow<List<Pokemon>> {
        return try {
            pokemonDao.getItemsCart().map { entities ->
                entities.map { it.toDomain() }
            }
        } catch (exception: Exception) {
            Log.e("IPokemonRepository", "Error searching pokemon cart", exception)
            throw Exception("Error searching pokemon cart", exception)
        }
    }
}