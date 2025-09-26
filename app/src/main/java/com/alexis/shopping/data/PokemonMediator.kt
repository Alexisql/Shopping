package com.alexis.shopping.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.alexis.shopping.data.local.PokemonDataBase
import com.alexis.shopping.data.local.entity.PokemonEntity
import com.alexis.shopping.data.remote.dto.toEntity
import com.alexis.shopping.data.remote.service.PokemonService
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PokemonMediator @Inject constructor(
    private val api: PokemonService,
    private val db: PokemonDataBase
) : RemoteMediator<Int, PokemonEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 0 else (lastItem.id / state.config.pageSize) + 20
                }
            }

            val response = api.getPokemons(
                offset = loadKey,
                limit = state.config.pageSize
            ).results

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.getPokemonDao().clearPokemon()
                }
                val pokemons = response.map { pokemonDto ->
                    pokemonDto.toEntity()
                }
                db.getPokemonDao().insertAll(pokemons)
            }

            MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpRetryException) {
            MediatorResult.Error(e)
        }
    }
}