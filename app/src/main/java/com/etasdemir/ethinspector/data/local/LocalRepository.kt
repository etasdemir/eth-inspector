package com.etasdemir.ethinspector.data.local

import com.etasdemir.ethinspector.data.domain_model.User
import com.etasdemir.ethinspector.data.local.dao.*
import com.etasdemir.ethinspector.data.local.entity.EthStatsEntity
import com.etasdemir.ethinspector.utils.Installation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val ethStatsDao: EthStatsDao,
    private val addressDao: AddressDao,
    private val userDao: UserDao,
    private val installation: Installation
) {

    suspend fun saveEthStats(ethStats: EthStatsEntity) {
        ethStatsDao.saveEthStats(ethStats)
    }

    suspend fun getEthStats(): EthStatsEntity {
        return ethStatsDao.getEthStats()
    }

    suspend fun getUser() {
//        return userDao
    }

    suspend fun saveUser(newUser: User) {

    }
}