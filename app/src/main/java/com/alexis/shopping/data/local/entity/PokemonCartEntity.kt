package com.alexis.shopping.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PokemonCartEntity(
    @Embedded
    val cart: CartEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val pokemon: PokemonEntity
)
