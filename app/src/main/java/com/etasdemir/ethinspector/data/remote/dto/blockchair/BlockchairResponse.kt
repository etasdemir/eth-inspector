package com.etasdemir.ethinspector.data.remote.dto.blockchair

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BlockchairResponse<T> (
    val data: T? = null,

    @Json(name = "context")
    val metadata: Metadata? = null
)