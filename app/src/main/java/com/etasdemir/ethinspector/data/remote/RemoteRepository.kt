package com.etasdemir.ethinspector.data.remote

import com.etasdemir.ethinspector.data.remote.dao.EthStatsDao
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val ethStatsDao: EthStatsDao
) {
    suspend fun getEthStats() {
        val response = ethStatsDao.getEthStats()
        if (response.isSuccessful) {
            Timber.e("result: ${response.body()}")
        } else {
            Timber.e("error: ${response.errorBody()}")
        }
    }
}