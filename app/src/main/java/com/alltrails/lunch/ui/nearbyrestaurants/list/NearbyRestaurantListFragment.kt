package com.alltrails.lunch.ui.nearbyrestaurants.list

import android.Manifest
import android.annotation.SuppressLint
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
import com.alltrails.lunch.noResultsRow
import com.alltrails.lunch.utils.viewBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.snackbar.Snackbar
import com.gun0912.tedpermission.coroutine.TedPermission
import kotlinx.coroutines.launch

class NearbyRestaurantListFragment : Fragment(R.layout.fragment_nearby_restaurant_list), MavericksView {
    private val binding: FragmentNearbyRestaurantListBinding by viewBinding()
    private val viewModel: NearbyRestaurantListViewModel by fragmentViewModel()

    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private val cancellationTokenSource = CancellationTokenSource()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNearbyRestaurants()
    }

    override fun onStop() {
        cancellationTokenSource.cancel()
        super.onStop()
    }

    @SuppressLint("MissingPermission")
    private fun getNearbyRestaurants() {
        lifecycleScope.launch {
            val permissionResult =
                TedPermission.create()
                    .setPermissions(locationPermission)
                    .setGotoSettingButton(true)
                    .setDeniedTitle(R.string.location_permission_denied_title)
                    .setDeniedMessage(R.string.location_permission_denied_body)
                    .check()
            if (permissionResult.isGranted) {
                fusedLocationClient.getCurrentLocation(PRIORITY_BALANCED_POWER_ACCURACY, cancellationTokenSource.token)
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        viewModel.searchNearby(LatLngLiteral(lat = location.latitude, lng = location.longitude))
                    } else {
                        showNoResults()
                    }
                }
            }
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        when (val response = state.response) {
            Uninitialized -> { }
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
            NetworkState.InvalidData -> { showNoResults() }
            is NetworkState.NetworkException -> handleError(response.error)
            is NetworkState.Success -> {
                if (response.data.results.isNullOrEmpty())
                    showNoResults()
                else
                    updateList(response.data.results)
            }
            null -> { showNoResults() }
        }
    }

    private fun showLoading() {
        binding.epoxyrecyclerviewNearbyList.withModels {
            loadingRow { id("loading") }
        }
    }

    private fun showNoResults() {
        binding.epoxyrecyclerviewNearbyList.withModels {
            noResultsRow { id("noResults") }
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