package com.alexis.shopping.data.remote.dto

import com.alexis.shopping.data.local.entity.PokemonEntity

private const val urlImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/"

data class PokemonDto(
    val name: String,
    val url: String
)

fun PokemonDto.toEntity() =
    PokemonEntity(
        id = url.trimEnd('/').substringAfterLast('/').toInt(),
        name = name,
        url = urlImage
    )