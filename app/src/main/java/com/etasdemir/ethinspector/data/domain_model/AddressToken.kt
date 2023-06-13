package com.etasdemir.ethinspector.data.domain_model

data class AddressToken(
    val ownerAddress: String,
    val name: String,
    val symbol: String,
    val tokenAddress: String,
    val quantity: Double
)
