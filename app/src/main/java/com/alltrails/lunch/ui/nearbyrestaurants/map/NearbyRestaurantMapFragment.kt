package com.alltrails.lunch.ui.nearbyrestaurants.map

import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.alltrails.lunch.R
import com.alltrails.lunch.databinding.FragmentNearbyRestaurantMapBinding
import com.alltrails.lunch.utils.viewBinding

class NearbyRestaurantMapFragment : Fragment(R.layout.fragment_nearby_restaurant_map),
    MavericksView {
    private val binding: FragmentNearbyRestaurantMapBinding by viewBinding()
    private val viewModel: NearbyRestaurantMapViewModel by fragmentViewModel()

    override fun invalidate() = withState(viewModel) { state ->

    }
}