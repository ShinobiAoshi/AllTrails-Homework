package com.alltrails.lunch.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Place(
    @field:Json(name = "place_id") val id: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "price_level") val priceLevel: Int?,
    @field:Json(name = "rating") val rating: Double?,
    @field:Json(name = "user_ratings_total") val userRatingsTotal: Int?,
    @field:Json(name = "website") val website: String?
)
