package com.alltrails.lunch.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Place(
    @field:Json(name = "place_id") val id: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "price_level") val priceLevel: Int?,
    @field:Json(name = "rating") val rating: Float?,
    @field:Json(name = "user_ratings_total") val userRatingsTotal: Int?,
    @field:Json(name = "photos") val photos: List<PlacePhoto>?,
    @field:Json(name = "geometry") val geometry: Geometry?,
) {
    // TODO: Replace API key
    val imageUrl: String =
        "https://maps.googleapis.com/maps/api/place/photo" +
        "?maxwidth=400" +
        "&photo_reference=${photos?.get(0)?.id}" +
        "&key=AIzaSyDQSd210wKX_7cz9MELkxhaEOUhFP0AkSk"
}

@JsonClass(generateAdapter = true)
data class PlacePhoto(
    @field:Json(name = "height") val height: Int?,
    @field:Json(name = "html_attributions") val htmlAttributions: List<String>?,
    @field:Json(name = "photo_reference") val id: String?,
    @field:Json(name = "width") val width: Int?
)

@JsonClass(generateAdapter = true)
data class Geometry(
    @field:Json(name = "location") val location: LatLngLiteral?,
    @field:Json(name = "viewport") val viewport: Bounds?
)

@JsonClass(generateAdapter = true)
data class LatLngLiteral(
    @field:Json(name = "lat") val lat: Double?,
    @field:Json(name = "lng") val lng: Double?
) {
    override fun toString(): String = String.format("%.1f,%.1f", lat, lng)
}

@JsonClass(generateAdapter = true)
data class Bounds(
    @field:Json(name = "northeast") val northeast: LatLngLiteral?,
    @field:Json(name = "southwest") val southwest: LatLngLiteral?
)
