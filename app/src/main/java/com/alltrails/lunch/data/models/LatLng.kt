package com.alltrails.lunch.data.models

data class LatLng(
    private val latitude: Double = 0.0,
    private val longitude: Double = 0.0
) {
    override fun toString(): String {
        return String.format("%.1f,%.1f", latitude, longitude)
    }
}