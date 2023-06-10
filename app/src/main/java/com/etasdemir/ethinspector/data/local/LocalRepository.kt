package com.etasdemir.ethinspector.data.local

import com.etasdemir.ethinspector.data.local.dao.AddressDaoLocal
import com.etasdemir.ethinspector.utils.Installation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val addressDaoLocal: AddressDaoLocal,
    private val installation: Installation
) {

    suspend fun saveEthStats() {

    }

    suspend fun getEthStats() {

    }

    suspend fun saveUserSettings() {
        val installationId = installation.id()
    }
}