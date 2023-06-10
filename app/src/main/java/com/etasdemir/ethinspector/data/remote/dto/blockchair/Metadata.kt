package com.etasdemir.ethinspector.data.remote.dto.blockchair

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Metadata(
    @field:Json(name = "code") val code: Int? = null,
    @field:Json(name = "error") val error: String? = null,
    @field:Json(name = "market_price_usd") val marketPriceUsd: Double? = null,
)