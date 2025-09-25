package com.alexis.shopping.data.remote.service

import com.alexis.shopping.data.remote.dto.PokemonResult
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonResult

}