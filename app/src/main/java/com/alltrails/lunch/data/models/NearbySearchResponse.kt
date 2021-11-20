package com.alltrails.lunch.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NearbySearchResponse(
    @field:Json(name = "html_attributions") val htmlAttributions: List<String>,
    @field:Json(name = "results") val results: List<Place>,
    @field:Json(name = "status") val status: PlacesSearchStatus,
    @field:Json(name = "error_message") val errorMessage: String?,
    @field:Json(name = "info_messages") val infoMessages: List<String>?,
    @field:Json(name = "next_page_token") val nextPageToken: String?
)

@JsonClass(generateAdapter = false)
enum class PlacesSearchStatus {
    OK,
    ZERO_RESULTS,
    INVALID_REQUEST,
    OVER_QUERY_LIMIT,
    REQUEST_DENIED,
    UNKNOWN_ERROR
}
