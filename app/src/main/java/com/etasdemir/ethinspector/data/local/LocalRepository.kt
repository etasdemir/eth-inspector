package com.etasdemir.ethinspector.data.local

import com.etasdemir.ethinspector.data.local.dao.AddressDaoLocal
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val addressDaoLocal: AddressDaoLocal
) {
    suspend fun saveContract() {
        Timber.e("save contract ${addressDaoLocal.javaClass}")
    }
}