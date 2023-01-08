package com.etasdemir.ethinspector.data.remote.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenericResponse<T> (
    val data: T? = null,
    val context: Context? = null
)