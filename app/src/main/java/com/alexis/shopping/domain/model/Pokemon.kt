package com.alexis.shopping.domain.model

import com.alexis.shopping.data.local.entity.CartEntity
import com.alexis.shopping.data.local.entity.PokemonCartEntity

data class Pokemon(
    val id: Int,
    val name: String,
    val url: String,
    val price: Double
)

fun Pokemon.toEntity() =
    CartEntity(
        id = id,
        name = name,
        url = url,
        price = price
    )

fun PokemonCartEntity.toDomain() = Pokemon(
    id = pokemon.id,
    name = pokemon.name,
    url = cart.url,
    price = cart.price
)

