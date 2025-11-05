package com.aneeq.starwarsplanets.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PlanetResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Planet>
)

@Parcelize
data class Planet(
    val name: String,
    val climate: String,
    val population: String,
    val diameter: String,
    val gravity: String,
    val terrain: String
) : Parcelable