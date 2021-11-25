package com.alltrails.lunch.ui.nearbyrestaurants.map

import android.app.Activity
import android.view.View
import com.alltrails.lunch.data.models.Place
import com.alltrails.lunch.databinding.NearbyRestaurantInfoWindowBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class NearbyRestaurantInfoWindowAdapter(context: Activity) : GoogleMap.InfoWindowAdapter {

    private val binding = NearbyRestaurantInfoWindowBinding.inflate(context.layoutInflater)

    override fun getInfoWindow(marker: Marker): View {
        binding.place = marker.tag as Place
        binding.executePendingBindings()
        return binding.root
    }

    override fun getInfoContents(marker: Marker): View? {
        return null
    }
}