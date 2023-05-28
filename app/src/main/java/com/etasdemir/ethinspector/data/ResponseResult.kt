package com.etasdemir.ethinspector.data

import retrofit2.Response

sealed class ResponseResult<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T) : ResponseResult<T>(data)

    class Error<T>(errorMessage: String) : ResponseResult<T>(null, errorMessage)

    class Loading<T> : ResponseResult<T>()
}

inline fun <reified T>retrofitResponseResultFactory(requestFunction: () -> Response<*>): ResponseResult<T> {
    try {
        val response = requestFunction()
        val body = response.body()
        val errorBody = response.errorBody()
        if (response.isSuccessful && body != null && body is T) {
            return ResponseResult.Success(body)
        } else if (errorBody != null) {
            return ResponseResult.Error(errorBody.string())
        }
    } catch (exception: Exception) {
        return ResponseResult.Error(exception.stackTraceToString())
    }
    return ResponseResult.Error("Unknown error at: ${Thread.currentThread().stackTrace}")
}
