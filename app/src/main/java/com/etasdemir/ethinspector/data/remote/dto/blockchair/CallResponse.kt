package com.etasdemir.ethinspector.data.remote.dto.blockchair

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CallResponse (
    @Json(name = "block_id")
    val blockId: Long,
    @Json(name = "transaction_hash")
    val transactionHash: String?,
    val time: String,
    val sender: String?,
    val recipient: String?,
    val value: Double,
    @Json(name = "value_usd")
    val valueUsd: Double?,
    val transferred: Boolean?,
)