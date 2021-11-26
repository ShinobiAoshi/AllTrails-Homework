package com.alltrails.lunch.ui.nearbyrestaurants

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.alltrails.lunch.utils.onSearch
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.gun0912.tedpermission.coroutine.TedPermission
import kotlinx.coroutines.launch
import com.alltrails.lunch.ui.nearbyrestaurants.View.*

class NearbyRestaurantListFragment : Fragment(R.layout.fragment_nearby_restaurant_list), MavericksView {
    private val viewModel: NearbyRestaurantViewModel by fragmentViewModel()
    private lateinit var binding: FragmentNearbyRestaurantListBinding

    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private val cancellationTokenSource = CancellationTokenSource()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentNearbyRestaurantListBinding.inflate(inflater, container, false)
        binding.mapviewNearbyRestaurants.onCreate(savedInstanceState)

        initView()
        getNearbyRestaurants()

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapviewNearbyRestaurants.onSaveInstanceState(outState)
    }

    private fun initView() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.header.edittextSearch.onSearch {
            searchNearbyRestaurants(binding.header.edittextSearch.text.toString())
        }

        binding.fabMap.setOnClickListener {
            viewModel.setView(MAP)
        }

        binding.fabList.setOnClickListener {
            viewModel.setView(LIST)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(afterSuccess: (location: Location?) -> Unit) {
        lifecycleScope.launch {
            val permissionResult =
                TedPermission.create()
                    .setPermissions(locationPermission)
                    .setGotoSettingButton(true)
                    .setDeniedTitle(R.string.location_permission_denied_title)
                    .setDeniedMessage(R.string.location_permission_denied_body)
                    .check()
            if (permissionResult.isGranted) {
                fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, cancellationTokenSource.token).addOnSuccessListener { location ->
                    afterSuccess.invoke(location)
                }
            }
        }
    }

    private fun getNearbyRestaurants() {
        getCurrentLocation { location ->
            if (location != null) {
                viewModel.searchNearby(LatLngLiteral(lat = location.latitude, lng = location.longitude))
            } else {
                showNoResults()
            }
        }
    }

    private fun searchNearbyRestaurants(query: String) {
        getCurrentLocation { location ->
            if (location != null) {
                viewModel.searchNearby(LatLngLiteral(lat = location.latitude, lng = location.longitude), query)
            } else {
                showNoResults()
            }
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        when (state.view) {
            LIST -> {
                binding.groupList.visibility = View.VISIBLE
                binding.groupMap.visibility = View.GONE
            }
            MAP -> {
                binding.groupList.visibility = View.GONE
                binding.groupMap.visibility = View.VISIBLE
            }
        }

        when (val response = state.response) {
            Uninitialized -> { }
            is Loading -> { showLoading() }
            is Success -> handleSuccess(response())
            is Fail -> handleError(response.error.message ?: "")
        }
    }

    private fun handleSuccess(response: NetworkState<NearbySearchResponse>?) {
        withState(viewModel) { state ->
            when (response) {
                is NetworkState.Success -> {
                    showResults(state.view, response.data.results)
                }
                is NetworkState.Error -> handleError(response.error)
                is NetworkState.HttpErrors.BadGateWay -> handleError(response.exception)
                is NetworkState.HttpErrors.InternalServerError -> handleError(response.exception)
                is NetworkState.HttpErrors.RemovedResourceFound -> handleError(response.exception)
                is NetworkState.HttpErrors.ResourceForbidden -> handleError(response.exception)
                is NetworkState.HttpErrors.ResourceNotFound -> handleError(response.exception)
                is NetworkState.HttpErrors.ResourceRemoved -> handleError(response.exception)
                is NetworkState.NetworkException -> handleError(response.error)
                NetworkState.InvalidData, null -> {
                    when(state.view) {
                        LIST -> { showNoResults() }
                        MAP -> {}
                    }
                }
            }
        }
    }

    private fun showResults(view: com.alltrails.lunch.ui.nearbyrestaurants.View, response: List<Place>) {
        when(view) {
            LIST -> {
                if (response.isNullOrEmpty())
                    showNoResults()
                else {
                    updateList(response)
                }
            }
            MAP -> { updateMap(response) }
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

    private fun updateMap(restaurants: List<Place>) {
        withState(viewModel) { state ->
            lifecycleScope.launch {
                val map = binding.mapviewNearbyRestaurants.awaitMap()
                drawMap(map, restaurants, state)
            }
        }
    }

    private fun drawMap(map: GoogleMap, restaurants: List<Place>, state: NearbyRestaurantState) {
        var boundsIncluded = false
        map.clear()
        map.setInfoWindowAdapter(NearbyRestaurantInfoWindowAdapter(requireActivity()))
        val bounds = LatLngBounds.builder()
        restaurants.forEach { restaurant ->
            val marker = initMarker(map, restaurant, state)
            if (marker != null) {
                bounds.include(marker.position)
                boundsIncluded = true
            }
        }
        map.setOnMarkerClickListener { marker ->
            viewModel.setSelectedRestaurant(marker.tag as Place)
            true
        }
        if (state.selectedRestaurant == null && boundsIncluded) {
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
        }
    }

    private fun initMarker(map: GoogleMap, restaurant: Place, state: NearbyRestaurantState): Marker? {
        val activeIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker_active)
        val inactiveIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker_inactive)
        val latLng = restaurant.geometry?.location?.toLatLng()
        if (latLng != null) {
            val marker = map.addMarker {
                title(restaurant.name)
                position(latLng)
            }
            with(marker) {
                tag = restaurant
                if (state.selectedRestaurant == restaurant) {
                    map.moveCamera(CameraUpdateFactory.newLatLng(position))
                    setIcon(activeIcon)
                    showInfoWindow()
                } else {
                    setIcon(inactiveIcon)
                }
            }
            return marker
        }
        return null
    }

    override fun onResume() {
        super.onResume()
        binding.mapviewNearbyRestaurants.onResume()
    }

    override fun onPause() {
        binding.mapviewNearbyRestaurants.onPause()
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
        binding.mapviewNearbyRestaurants.onStart()
    }

    override fun onStop() {
        binding.mapviewNearbyRestaurants.onStop()
        cancellationTokenSource.cancel()
        super.onStop()
    }

    override fun onDestroy() {
        binding.mapviewNearbyRestaurants.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        binding.mapviewNearbyRestaurants.onLowMemory()
        super.onLowMemory()
    }
}