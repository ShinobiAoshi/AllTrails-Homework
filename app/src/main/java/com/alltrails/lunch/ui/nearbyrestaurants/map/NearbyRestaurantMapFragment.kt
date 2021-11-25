package com.alltrails.lunch.ui.nearbyrestaurants.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.*
import com.alltrails.lunch.R
import com.alltrails.lunch.data.models.LatLngLiteral
import com.alltrails.lunch.data.models.NearbySearchResponse
import com.alltrails.lunch.data.models.Place
import com.alltrails.lunch.databinding.FragmentNearbyRestaurantMapBinding
import com.alltrails.lunch.network.models.NetworkState
import com.alltrails.lunch.ui.nearbyrestaurants.NearbyRestaurantFragment
import com.alltrails.lunch.ui.nearbyrestaurants.list.NearbyRestaurantState
import com.alltrails.lunch.utils.onSearch
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch

class NearbyRestaurantMapFragment : NearbyRestaurantFragment(R.layout.fragment_nearby_restaurant_map) {
    private lateinit var binding: FragmentNearbyRestaurantMapBinding

    override fun invalidate() = withState(viewModel) { state ->
        when (val response = state.response) {
            Uninitialized -> { }
            is Loading -> { showLoading() }
            is Success -> handleSuccess(response())
            is Fail -> handleError(response.error.message ?: "")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapviewNearbyRestaurants.onSaveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentNearbyRestaurantMapBinding.inflate(inflater, container, false)
        binding.mapviewNearbyRestaurants.onCreate(savedInstanceState)

        initView()

        return binding.root
    }

    override fun initView() {
        super.initView()

        binding.fabList.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.header.edittextSearch.onSearch {
            searchNearbyRestaurants(binding.header.edittextSearch.text.toString())
        }
    }

    private fun searchNearbyRestaurants(query: String) {
        getCurrentLocation { location ->
            if (location != null) {
                viewModel.searchNearby(LatLngLiteral(lat = location.latitude, lng = location.longitude), query)
            }
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
            NetworkState.InvalidData -> { }
            is NetworkState.NetworkException -> handleError(response.error)
            is NetworkState.Success -> {
                if (!response.data.results.isNullOrEmpty()) updateMap(response.data.results)
            }
            null -> { }
        }
    }

    private fun updateMap(restaurants: List<Place>) {
        binding.loading.root.visibility = View.GONE
        binding.groupMap.visibility = View.VISIBLE
        withState(viewModel) { state ->
            lifecycleScope.launch {
                val map = binding.mapviewNearbyRestaurants.awaitMap()
                drawMap(map, restaurants, state)
            }
        }
    }

    private fun drawMap(map: GoogleMap, restaurants: List<Place>, state: NearbyRestaurantState) {
        map.clear()
        map.setInfoWindowAdapter(NearbyRestaurantInfoWindowAdapter(requireActivity()))
        val bounds = LatLngBounds.builder()
        restaurants.forEach { restaurant ->
            val marker = initMarker(map, restaurant, state)
            if (marker != null) bounds.include(marker.position)
        }
        map.setOnMarkerClickListener { marker ->
            viewModel.setSelectedRestaurant(marker.tag as Place)
            true
        }
        if (state.selectedRestaurant == null) {
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

    private fun handleError(exception: String) {
        Snackbar.make(binding.root, exception, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading() {
        binding.loading.root.visibility = View.VISIBLE
        binding.groupMap.visibility = View.GONE
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