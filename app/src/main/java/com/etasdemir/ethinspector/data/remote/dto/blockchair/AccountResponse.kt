package com.etasdemir.ethinspector.data.remote.dto.blockchair

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountResponse(
    @Json(name = "balance")
    val balanceWei: String,
    @Json(name = "balance_usd")
    val balanceUsd: Double,
    @Json(name = "received_approximate")
    val receivedApproximate: String,
    @Json(name = "received_usd")
    val receivedUsd: Double,
    @Json(name = "spent_approximate")
    val spentApproximate: String,
    @Json(name = "spent_usd")
    val spentUsd: Double,
    @Json(name = "fees_approximate")
    val feesApproximate: String,
    @Json(name = "fees_usd")
    val feesUsd: Double,
    @Json(name = "receiving_call_count")
    val receivingCallCount: Long,
    @Json(name = "spending_call_count")
    val spendingCallCount: Long,
    @Json(name = "call_count")
    val callCount: Long,
    @Json(name = "transaction_count")
    val transactionCount: Long,
    @Json(name = "first_seen_receiving")
    val firstSeenReceiving: String?,
    @Json(name = "last_seen_receiving")
    val lastSeenReceiving: String?,
    @Json(name = "first_seen_spending")
    val firstSeenSpending: String?,
    @Json(name = "last_seen_spending")
    val lastSeenSpending: String?,
)