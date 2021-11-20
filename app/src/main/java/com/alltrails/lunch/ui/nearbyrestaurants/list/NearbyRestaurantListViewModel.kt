package com.alltrails.lunch.ui.nearbyrestaurants.list

import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.alltrails.lunch.data.models.LatLng
import com.alltrails.lunch.data.repository.NearbySearchRepository
import com.alltrails.lunch.di.utils.AssistedViewModelFactory
import com.alltrails.lunch.di.utils.hiltMavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

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

    fun searchNearby(location: LatLng) = withState { state ->
        if (state.response is Loading) return@withState
        nearbySearchRepository.searchNearby(location).execute() { copy(response = it) }
    }
}