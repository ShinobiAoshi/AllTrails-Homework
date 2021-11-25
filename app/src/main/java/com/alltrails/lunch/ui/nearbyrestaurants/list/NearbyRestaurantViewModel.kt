package com.alltrails.lunch.ui.nearbyrestaurants.list

import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.alltrails.lunch.data.repository.NearbySearchRepository
import com.alltrails.lunch.di.utils.AssistedViewModelFactory
import com.alltrails.lunch.di.utils.hiltMavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.alltrails.lunch.data.models.LatLngLiteral
import com.alltrails.lunch.data.models.NearbySearchResponse
import com.alltrails.lunch.network.models.NetworkState

data class NearbyRestaurantState(
    val response: Async<NetworkState<NearbySearchResponse>> = Uninitialized
) : MavericksState

class NearbyRestaurantViewModel @AssistedInject constructor(
    @Assisted initialState: NearbyRestaurantState,
    private val nearbySearchRepository: NearbySearchRepository
) : MavericksViewModel<NearbyRestaurantState>(initialState) {

    companion object : MavericksViewModelFactory<NearbyRestaurantViewModel, NearbyRestaurantState> by hiltMavericksViewModelFactory()

    @AssistedFactory
    interface Factory :
        AssistedViewModelFactory<NearbyRestaurantViewModel, NearbyRestaurantState> {
        override fun create(state: NearbyRestaurantState): NearbyRestaurantViewModel
    }

    fun searchNearby(location: LatLngLiteral, query: String? = null) = withState { state ->
        if (state.response is Loading) return@withState
        nearbySearchRepository.searchNearby(location, query).execute() { copy(response = it) }
    }
}