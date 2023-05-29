package com.etasdemir.ethinspector.data.remote

import okhttp3.Interceptor


fun apiKeyInterceptor(field: String, key: String): Interceptor {
    return Interceptor { chain ->
        val request = chain.request()
        val url = request.url
        val newUrl = url.newBuilder()
            .addQueryParameter(field, key)
            .build()
        val interceptedRequest = request
            .newBuilder()
            .url(newUrl)
            .build()
        return@Interceptor chain.proceed(interceptedRequest)
    }
}
