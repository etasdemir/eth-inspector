package com.etasdemir.ethinspector.data.remote.dao

interface BlockDao {

    suspend fun getBlockInfoByNumber(blockNumber: String) {}

}