package com.alltrails.lunch.ui.nearbyrestaurants.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.navigation.navGraphViewModel
import com.airbnb.mvrx.withState
import com.alltrails.lunch.R
import com.alltrails.lunch.databinding.FragmentNearbyRestaurantMapBinding
import com.alltrails.lunch.ui.nearbyrestaurants.list.NearbyRestaurantViewModel
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad

class NearbyRestaurantMapFragment : Fragment(R.layout.fragment_nearby_restaurant_map),
    MavericksView {
    private lateinit var binding: FragmentNearbyRestaurantMapBinding
     private val viewModel: NearbyRestaurantViewModel by navGraphViewModel(R.id.navigation_alltrails_lunch)

    override fun invalidate() = withState(viewModel) { state ->

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

    private fun initView() {
        binding.fabList.setOnClickListener {
            findNavController().navigate(R.id.fragment_nearby_restaurant_list_destination)
        }

        lifecycleScope.launchWhenCreated {
            val googleMap = binding.mapviewNearbyRestaurants.awaitMap()

            googleMap.awaitMapLoad()

            // Ensure all places are visible in the map
//            val bounds = LatLngBounds.builder()
//            places.forEach { bounds.include(it.latLng) }
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
//
//            addClusteredMarkers(googleMap)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapviewNearbyRestaurants.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapviewNearbyRestaurants.onPause()
    }

    override fun onStart() {
        super.onStart()
        binding.mapviewNearbyRestaurants.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapviewNearbyRestaurants.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapviewNearbyRestaurants.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapviewNearbyRestaurants.onLowMemory()
    }
}