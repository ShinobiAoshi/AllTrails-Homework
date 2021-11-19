package com.alltrails.lunch.ui.nearbyrestaurants.list

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.alltrails.lunch.di.utils.AssistedViewModelFactory
import com.alltrails.lunch.di.utils.hiltMavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class NearbyRestaurantListViewModel @AssistedInject constructor(
    @Assisted initialState: NearbyRestaurantListState
) : MavericksViewModel<NearbyRestaurantListState>(initialState) {

    @AssistedFactory
    interface Factory :
        AssistedViewModelFactory<NearbyRestaurantListViewModel, NearbyRestaurantListState> {
        override fun create(state: NearbyRestaurantListState): NearbyRestaurantListViewModel
    }

    companion object : MavericksViewModelFactory<NearbyRestaurantListViewModel, NearbyRestaurantListState> by hiltMavericksViewModelFactory()
}