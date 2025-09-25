package com.alexis.shopping.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexis.shopping.domain.model.Pokemon

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String
)

fun PokemonEntity.toDomain() =
    Pokemon(
        id = id,
        name = name,
        url = "$url${id}.png",
        price = (id * 2).toDouble()
    )
