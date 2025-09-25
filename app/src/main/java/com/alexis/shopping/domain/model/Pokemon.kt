package com.alexis.shopping.domain.model

import com.alexis.shopping.data.local.entity.CartEntity

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

fun CartEntity.toDomain() =
    Pokemon(
        id = id,
        name = name,
        url = url,
        price = price
    )

