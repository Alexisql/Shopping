package com.alexis.shopping.data.remote.dto

data class PokemonResult(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<PokemonDto>
)
