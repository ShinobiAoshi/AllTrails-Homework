package com.alltrails.lunch.data.repository

import android.content.Context
import com.alltrails.lunch.R
import com.alltrails.lunch.data.models.LatLngLiteral
import com.alltrails.lunch.data.models.NearbySearchResponse
import com.alltrails.lunch.data.models.PlacesSearchStatus
import com.alltrails.lunch.network.service.NearbySearchService
import com.alltrails.lunch.network.models.NetworkState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NearbySearchRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val nearbySearchService: NearbySearchService
) {

    fun searchNearby(location: LatLngLiteral): Flow<NetworkState<NearbySearchResponse>> {
        return flow {
            val response = nearbySearchService.nearbySearch(location)
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) emit(NetworkState.InvalidData)
                else {
                    when (body.status) {
                        PlacesSearchStatus.OK,
                        PlacesSearchStatus.ZERO_RESULTS -> {
                            emit(NetworkState.Success(body))
                        }
                        PlacesSearchStatus.INVALID_REQUEST,
                        PlacesSearchStatus.OVER_QUERY_LIMIT,
                        PlacesSearchStatus.REQUEST_DENIED,
                        PlacesSearchStatus.UNKNOWN_ERROR -> {
                            emit(NetworkState.Error(body.errorMessage ?: context.getString(R.string.error_invalid_request)))
                        }
                    }
                }
            } else {
                when (response.code()) {
                    403 -> emit(NetworkState.HttpErrors.ResourceForbidden(response.message()))
                    404 -> emit(NetworkState.HttpErrors.ResourceNotFound(response.message()))
                    500 -> emit(NetworkState.HttpErrors.InternalServerError(response.message()))
                    502 -> emit(NetworkState.HttpErrors.BadGateWay(response.message()))
                    301 -> emit(NetworkState.HttpErrors.ResourceRemoved(response.message()))
                    302 -> emit(NetworkState.HttpErrors.RemovedResourceFound(response.message()))
                    else -> emit(NetworkState.Error(response.message()))
                }
            }
        }.catch { e ->
            emit(NetworkState.NetworkException(e.message!!))
        }.flowOn(Dispatchers.IO)
    }
}