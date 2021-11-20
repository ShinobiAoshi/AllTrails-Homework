package com.alltrails.lunch.network.models

sealed class NetworkState<out T> {
    data class Success<out T>(val data: T) : NetworkState<T>()
    object InvalidData: NetworkState<Nothing>()
    data class Error<out T>(val error: String) : NetworkState<T>()
    data class NetworkException<out T>(val error: String) : NetworkState<T>()
    sealed class HttpErrors<out T>: NetworkState<T>() {
        data class ResourceForbidden<out T>(val exception: String): HttpErrors<T>()
        data class ResourceNotFound<out T>(val exception: String): HttpErrors<T>()
        data class InternalServerError<out T>(val exception: String): HttpErrors<T>()
        data class BadGateWay<out T>(val exception: String): HttpErrors<T>()
        data class ResourceRemoved<out T>(val exception: String): HttpErrors<T>()
        data class RemovedResourceFound<out T>(val exception: String): HttpErrors<T>()
    }
}