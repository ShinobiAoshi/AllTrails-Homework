package com.alltrails.lunch.ui.nearbyrestaurants

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.navigation.navGraphViewModel
import com.alltrails.lunch.R
import com.alltrails.lunch.ui.nearbyrestaurants.list.NearbyRestaurantViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.gun0912.tedpermission.coroutine.TedPermission
import kotlinx.coroutines.launch

abstract class NearbyRestaurantFragment(@LayoutRes val layoutRes: Int = 0) : Fragment(layoutRes), MavericksView {
    protected val viewModel: NearbyRestaurantViewModel by navGraphViewModel(R.id.navigation_alltrails_lunch)

    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private val cancellationTokenSource = CancellationTokenSource()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    open fun initView() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    @SuppressLint("MissingPermission")
    protected fun getCurrentLocation(afterSuccess: (location: Location?) -> Unit) {
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

    override fun onStop() {
        cancellationTokenSource.cancel()
        super.onStop()
    }
}