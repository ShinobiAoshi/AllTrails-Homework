package com.alltrails.lunch.ui.nearbyrestaurants.map

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.alltrails.lunch.di.utils.AssistedViewModelFactory
import com.alltrails.lunch.di.utils.hiltMavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class NearbyRestaurantMapViewModel @AssistedInject constructor(
    @Assisted initialState: NearbyRestaurantMapState
) : MavericksViewModel<NearbyRestaurantMapState>(initialState) {

    @AssistedFactory
    interface Factory :
        AssistedViewModelFactory<NearbyRestaurantMapViewModel, NearbyRestaurantMapState> {
        override fun create(state: NearbyRestaurantMapState): NearbyRestaurantMapViewModel
    }

    companion object : MavericksViewModelFactory<NearbyRestaurantMapViewModel, NearbyRestaurantMapState> by hiltMavericksViewModelFactory()
}