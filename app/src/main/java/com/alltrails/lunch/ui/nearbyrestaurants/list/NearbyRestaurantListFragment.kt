package com.alltrails.lunch.ui.nearbyrestaurants.list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
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
import com.alltrails.lunch.ui.nearbyrestaurants.NearbyRestaurantFragment
import com.alltrails.lunch.utils.onSearch
import com.alltrails.lunch.utils.viewBinding
import com.google.android.material.snackbar.Snackbar

class NearbyRestaurantListFragment : NearbyRestaurantFragment(R.layout.fragment_nearby_restaurant_list) {
    private val binding: FragmentNearbyRestaurantListBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getNearbyRestaurants()
    }

    override fun initView() {
        super.initView()

        binding.header.edittextSearch.onSearch {
            searchNearbyRestaurants(binding.header.edittextSearch.text.toString())
        }

        binding.fabMap.setOnClickListener {
            findNavController().navigate(R.id.fragment_nearby_restaurant_map_destination)
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