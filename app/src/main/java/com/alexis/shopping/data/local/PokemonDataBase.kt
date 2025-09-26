package com.alexis.shopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexis.shopping.data.local.dao.PokemonDao
import com.alexis.shopping.data.local.entity.CartEntity
import com.alexis.shopping.data.local.entity.PokemonEntity

@Database(entities = [PokemonEntity::class, CartEntity::class], version = 1)
abstract class PokemonDataBase : RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao
}