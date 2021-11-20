package com.alltrails.lunch.ui.nearbyrestaurants.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.alltrails.lunch.R
import com.alltrails.lunch.data.models.LatLng
import com.alltrails.lunch.data.models.NearbySearchResponse
import com.alltrails.lunch.databinding.FragmentNearbyRestaurantListBinding
import com.alltrails.lunch.network.models.NetworkState
import com.alltrails.lunch.utils.viewBinding
import com.google.android.material.snackbar.Snackbar

class NearbyRestaurantListFragment : Fragment(R.layout.fragment_nearby_restaurant_list), MavericksView {
    private val binding: FragmentNearbyRestaurantListBinding by viewBinding()
    private val viewModel: NearbyRestaurantListViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onAsync(NearbyRestaurantListState::response, uniqueOnly()) { nearbyPlacesResponse ->
            handleNearbyPlacesResult(nearbyPlacesResponse)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.searchNearby(LatLng(latitude = 30.445840, longitude = -97.688290))
    }

    override fun invalidate() = withState(viewModel) { state ->

    }

    private fun handleNearbyPlacesResult(response: NetworkState<NearbySearchResponse>) {
        when (response) {
            is NetworkState.Success -> TODO()
            is NetworkState.Error -> handleError(response.error)
            is NetworkState.HttpErrors.BadGateWay -> handleError(response.exception)
            is NetworkState.HttpErrors.InternalServerError -> handleError(response.exception)
            is NetworkState.HttpErrors.RemovedResourceFound -> handleError(response.exception)
            is NetworkState.HttpErrors.ResourceForbidden -> handleError(response.exception)
            is NetworkState.HttpErrors.ResourceNotFound -> handleError(response.exception)
            is NetworkState.HttpErrors.ResourceRemoved -> handleError(response.exception)
            NetworkState.InvalidData -> TODO()
            is NetworkState.NetworkException -> handleError(response.error)
        }
    }

    private fun handleError(exception: String) {
        Snackbar.make(binding.root, exception, Snackbar.LENGTH_LONG).show()
    }
}