package com.etasdemir.ethinspector.data

import com.etasdemir.ethinspector.data.local.LocalRepository
import com.etasdemir.ethinspector.data.remote.RemoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) {

//    suspend fun getEthStats(): ResponseResult<BlockchairResponse<EthStatsResponse>> {
//        val response = remoteRepository.getEthStats()
//        return if (response is ResponseResult.Success) {
//            // save
//            response
//        } else {
//            // retrieve and return
//        }
//    }

}