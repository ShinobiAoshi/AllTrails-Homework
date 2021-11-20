package com.alltrails.lunch.network.service

import com.alltrails.lunch.data.models.LatLngLiteral
import com.alltrails.lunch.data.models.NearbySearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NearbySearchService {

    @GET("nearbysearch/json?language=en&type=restaurant&radius=5000")
    suspend fun nearbySearch(@Query("location") location: LatLngLiteral) : Response<NearbySearchResponse>
}