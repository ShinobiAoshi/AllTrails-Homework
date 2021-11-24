package com.alltrails.lunch.ui.nearbyrestaurants.list

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.*
import com.alltrails.lunch.R
import com.alltrails.lunch.data.models.LatLngLiteral
import com.alltrails.lunch.data.models.NearbySearchResponse
import com.alltrails.lunch.data.models.Place
import com.alltrails.lunch.databinding.FragmentNearbyRestaurantListBinding
import com.alltrails.lunch.itemNearbyRestaurant
import com.alltrails.lunch.loadingRow
import com.alltrails.lunch.network.models.NetworkState
import com.alltrails.lunch.utils.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.gun0912.tedpermission.coroutine.TedPermission
import kotlinx.coroutines.launch

class NearbyRestaurantListFragment : Fragment(R.layout.fragment_nearby_restaurant_list), MavericksView {
    private val binding: FragmentNearbyRestaurantListBinding by viewBinding()
    private val viewModel: NearbyRestaurantListViewModel by fragmentViewModel()

    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            val permissionResult =
                TedPermission.create()
                    .setPermissions(locationPermission)
                    .setGotoSettingButton(true)
                    .setDeniedTitle(R.string.location_permission_denied_title)
                    .setDeniedMessage(R.string.location_permission_denied_body)
                    .check()
            if (permissionResult.isGranted) {
                viewModel.searchNearby(LatLngLiteral(lat = 30.445840, lng = -97.688290))
            }
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        when (val response = state.response) {
            Uninitialized -> {  }
            is Loading -> { showLoading() }
            is Success -> handleSuccess(response())
            is Fail -> handleError(response.error.message ?: "")
        }
    }

    private fun handleSuccess(response: NetworkState<NearbySearchResponse>?) {
        when (response) {
            is NetworkState.Error -> handleError(response.error)
            is NetworkState.HttpErrors.BadGateWay -> handleError(response.exception)
            is NetworkState.HttpErrors.InternalServerError -> handleError(response.exception)
            is NetworkState.HttpErrors.RemovedResourceFound -> handleError(response.exception)
            is NetworkState.HttpErrors.ResourceForbidden -> handleError(response.exception)
            is NetworkState.HttpErrors.ResourceNotFound -> handleError(response.exception)
            is NetworkState.HttpErrors.ResourceRemoved -> handleError(response.exception)
            NetworkState.InvalidData -> { /* Show empty state */ }
            is NetworkState.NetworkException -> handleError(response.error)
            is NetworkState.Success -> {
                updateList(response.data.results)
            }
            null -> { /* Show empty state */ }
        }
    }

    private fun showLoading() {
        binding.epoxyrecyclerviewNearbyList.withModels {
            loadingRow { id("loading") }
        }
    }

    private fun updateList(nearbyRestaurants: List<Place>) {
        binding.epoxyrecyclerviewNearbyList.withModels {
            nearbyRestaurants.forEach { restaurant ->
                itemNearbyRestaurant {
                    id(restaurant.id)
                    place(restaurant)
                }
            }
        }
    }

    private fun handleError(exception: String) {
        Snackbar.make(binding.root, exception, Snackbar.LENGTH_LONG).show()
    }
}