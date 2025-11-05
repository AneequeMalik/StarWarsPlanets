package com.aneeq.starwarsplanets.domain.remote

import com.aneeq.starwarsplanets.domain.model.PlanetResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("planets/")
    suspend fun getPlanets(@Query("page") page: Int): PlanetResponse
}