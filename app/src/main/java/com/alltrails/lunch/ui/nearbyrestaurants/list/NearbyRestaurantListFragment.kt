package com.alltrails.lunch.ui.nearbyrestaurants.list

import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.alltrails.lunch.R
import com.alltrails.lunch.databinding.FragmentNearbyRestaurantListBinding
import com.alltrails.lunch.utils.viewBinding

class NearbyRestaurantListFragment : Fragment(R.layout.fragment_nearby_restaurant_list), MavericksView {
    private val binding: FragmentNearbyRestaurantListBinding by viewBinding()
    private val viewModel: NearbyRestaurantListViewModel by fragmentViewModel()

    override fun invalidate() = withState(viewModel) { state ->

    }

}