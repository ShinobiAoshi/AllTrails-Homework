package com.alltrails.lunch.ui.nearbyrestaurants.list

import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.alltrails.lunch.data.models.Place
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

data class NearbyRestaurantListState(
    val nearbyRestaurants: List<Place> = emptyList(),
    val response: Async<NetworkState<NearbySearchResponse>> = Uninitialized
) : MavericksState

class NearbyRestaurantListViewModel @AssistedInject constructor(
    @Assisted initialState: NearbyRestaurantListState,
    private val nearbySearchRepository: NearbySearchRepository
) : MavericksViewModel<NearbyRestaurantListState>(initialState) {

    companion object : MavericksViewModelFactory<NearbyRestaurantListViewModel, NearbyRestaurantListState> by hiltMavericksViewModelFactory()

    @AssistedFactory
    interface Factory :
        AssistedViewModelFactory<NearbyRestaurantListViewModel, NearbyRestaurantListState> {
        override fun create(state: NearbyRestaurantListState): NearbyRestaurantListViewModel
    }

    fun searchNearby(location: LatLngLiteral) = withState { state ->
        if (state.response is Loading) return@withState
        nearbySearchRepository.searchNearby(location).execute() { copy(response = it) }
    }
}