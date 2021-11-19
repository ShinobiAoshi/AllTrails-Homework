package com.alltrails.lunch.ui.nearbyrestaurants.list

import com.airbnb.mvrx.MavericksState

data class NearbyRestaurantListState(val nearbyRestaurants: List<String> = emptyList()) : MavericksState