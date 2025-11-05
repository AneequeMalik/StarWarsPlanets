package com.aneeq.starwarsplanets.domain.repository

import com.aneeq.starwarsplanets.domain.model.PlanetResponse
import com.aneeq.starwarsplanets.domain.remote.APIService
import com.aneeq.starwarsplanets.utils.Logger
import com.google.gson.GsonBuilder
import javax.inject.Inject

class PlanetRepository @Inject constructor(
    private val api: APIService,
    private val logger: Logger
) {
    suspend fun getPlanets(page: Int): Result<PlanetResponse> = try {
        val response = api.getPlanets(page)

        try {
            val json = GsonBuilder().setPrettyPrinting().create().toJson(response)
            logger.debug(this, "Parsed JSON (pretty):\n$json")
            try {
                val json = GsonBuilder().setPrettyPrinting().create().toJson(response)
                logger.debug(this, "Parsed JSON (pretty):\n$json")
            } catch (e: Exception) {
                logger.warn(this, "Failed to pretty print JSON: ${e.localizedMessage}")
            }
        } catch (e: Exception) {
            logger.warn(this, "Failed to pretty print JSON: ${e.localizedMessage}")
        }
        Result.success(response)
    } catch (e: Exception) {
        logger.error(this, "Error fetching planets (page $page): ${e.localizedMessage}", e)
        Result.failure(e)
    }
}