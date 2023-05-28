package com.etasdemir.ethinspector.data.remote.dao

import retrofit2.Response

interface AddressDao {

    suspend fun getAccountInfoByHash(addressHash: String): Response<Any>

    suspend fun getContractInfoByHash(addressHash: String): Response<Any>

}