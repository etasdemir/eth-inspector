package com.etasdemir.ethinspector.ui

import com.etasdemir.ethinspector.data.ResponseResult

sealed class UIResponseState<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T) : UIResponseState<T>(data)

    class Error<T>(errorMessage: String) : UIResponseState<T>(null, errorMessage)

    class Loading<T> : UIResponseState<T>()
}

fun <T> mapResponseToUIResponseState(responseResult: ResponseResult<T>): UIResponseState<T> {
    return when (responseResult) {
        is ResponseResult.Success -> UIResponseState.Success(responseResult.data!!)

        is ResponseResult.Error -> UIResponseState.Error(responseResult.errorMessage!!)

        else -> UIResponseState.Loading()
    }
}
