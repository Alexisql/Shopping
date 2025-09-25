package com.alexis.shopping.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexis.shopping.data.local.entity.CartEntity
import com.alexis.shopping.data.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon ORDER BY name ASC")
    fun getAllPokemon(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE :pokemonName || '%' ORDER BY name")
    fun searchPokemon(pokemonName: String): PagingSource<Int, PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<PokemonEntity>)

    @Query("DELETE FROM pokemon")
    suspend fun clearPokemon()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartEntity)

    @Query("DELETE FROM cart WHERE id = :cartItemId")
    suspend fun deleteCartItemById(cartItemId: Int)

    @Query("SELECT * FROM cart ORDER BY name ASC")
    fun getItemsCart(): Flow<List<CartEntity>>
}