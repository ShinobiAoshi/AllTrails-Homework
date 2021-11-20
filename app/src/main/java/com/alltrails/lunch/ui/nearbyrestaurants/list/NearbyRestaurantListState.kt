package com.alltrails.lunch.ui.nearbyrestaurants.list

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.alltrails.lunch.data.models.NearbySearchResponse
import com.alltrails.lunch.data.models.Place
import com.alltrails.lunch.network.models.NetworkState

data class NearbyRestaurantListState(
    val nearbySearchResults: List<Place> = emptyList(),
    val response: Async<NetworkState<NearbySearchResponse>> = Uninitialized
) : MavericksState