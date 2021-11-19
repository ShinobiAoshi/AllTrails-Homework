package com.alltrails.lunch.di

import com.alltrails.lunch.di.utils.AssistedViewModelFactory
import com.alltrails.lunch.di.utils.MavericksViewModelComponent
import com.alltrails.lunch.di.utils.ViewModelKey
import com.alltrails.lunch.ui.nearbyrestaurants.list.NearbyRestaurantListViewModel
import com.alltrails.lunch.ui.nearbyrestaurants.map.NearbyRestaurantMapViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(NearbyRestaurantListViewModel::class)
    fun nearbyRestaurantListViewModelFactory(factory: NearbyRestaurantListViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(NearbyRestaurantMapViewModel::class)
    fun nearbyRestaurantMapViewModelFactory(factory: NearbyRestaurantMapViewModel.Factory): AssistedViewModelFactory<*, *>
}