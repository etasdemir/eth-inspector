package com.etasdemir.ethinspector.utils

import javax.inject.Inject

object Constants {
    const val BLOCK_CHAIR_URL = "https://api.blockchair.com"
    const val ETHER_SCAN_URL = "https://api.etherscan.io"
    const val ETHER_SCAN_API_KEY_QUERY_NAME = "apikey"
    const val REQUEST_TIMEOUT = 5000L

    const val TRANSACTION_HEX_LEN = 66
    const val ADDRESS_HEX_LEN = 42
}

enum class AddressType {
    CONTRACT, ACCOUNT
}