package com.alltrails.lunch.di

import com.alltrails.lunch.di.utils.AssistedViewModelFactory
import com.alltrails.lunch.di.utils.MavericksViewModelComponent
import com.alltrails.lunch.di.utils.ViewModelKey
import com.alltrails.lunch.ui.nearbyrestaurants.NearbyRestaurantViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(NearbyRestaurantViewModel::class)
    fun nearbyRestaurantViewModelFactory(factory: NearbyRestaurantViewModel.Factory): AssistedViewModelFactory<*, *>
}