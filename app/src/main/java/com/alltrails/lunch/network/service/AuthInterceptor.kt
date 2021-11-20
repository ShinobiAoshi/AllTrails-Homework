package com.alltrails.lunch.network.service

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Intercepts and adds Token or API Key
 */
class AuthInterceptor(private val apiKey: String) : Interceptor {

    companion object {
        private const val AUTH_FIELD_NAME = "key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val httpUrl = original.url.newBuilder()
            .addQueryParameter(AUTH_FIELD_NAME, apiKey)
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(httpUrl)

        return chain.proceed(requestBuilder.build())
    }
}