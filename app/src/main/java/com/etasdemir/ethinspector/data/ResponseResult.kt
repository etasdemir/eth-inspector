package com.etasdemir.ethinspector.data

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

sealed class ResponseResult<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T) : ResponseResult<T>(data)

    class Error<T>(errorMessage: String) : ResponseResult<T>(null, errorMessage)

    class Loading<T> : ResponseResult<T>()
}

inline fun <reified T> retrofitResponseResultFactory(
    bodyInterceptor: ((body: String) -> String?) = { body -> body },
    requestFunction: () -> Response<*>
): ResponseResult<T> {
    try {
        val response = requestFunction()
        var body = response.body()
        if (body is ResponseBody) {
            val stringBody = bodyInterceptor(body.string())
                ?: return ResponseResult.Error("Custom parsing error.")
            body = stringBody.toResponseBody(
                "application/json; charset-utf-8".toMediaTypeOrNull()
            )
        }
        val errorBody = response.errorBody()
        if (response.isSuccessful && body != null && body is T) {
            return ResponseResult.Success(body)
        } else if (errorBody != null) {
            return ResponseResult.Error(errorBody.string())
        }
    } catch (exception: Exception) {
        return ResponseResult.Error(exception.stackTraceToString())
    }
    val errorStringBuilder = StringBuilder()
    val stack = Thread.currentThread().stackTrace
    for (trace in stack) {
        errorStringBuilder.appendLine(trace.toString())
    }
    return ResponseResult.Error("Unknown error at: $errorStringBuilder")
}
