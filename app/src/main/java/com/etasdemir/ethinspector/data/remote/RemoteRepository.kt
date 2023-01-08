package com.etasdemir.ethinspector.data.remote

import com.etasdemir.ethinspector.data.remote.dao.EthStatsDao
import com.etasdemir.ethinspector.data.remote.entity.EthStats
import com.etasdemir.ethinspector.data.remote.entity.GenericResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val ethStatsDao: EthStatsDao
) {
    suspend fun getEthStats(): Flow<GenericResponse<EthStats>> {
        return flow {
            val response = ethStatsDao.getEthStats()
            Timber.e("getEthStats: ${response.raw()}")
            if (response.isSuccessful && response.body() != null) {
                emit(response.body()!!)
            } else {
                emit(response.body()!!)
            }
        }
    }
}