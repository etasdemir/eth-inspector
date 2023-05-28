package com.etasdemir.ethinspector.data.remote

import com.etasdemir.ethinspector.data.remote.dao.*
import com.etasdemir.ethinspector.data.remote.entity.SearchType
import com.etasdemir.ethinspector.data.remote.entity.blockchair.BlockchairResponse
import com.etasdemir.ethinspector.data.remote.entity.blockchair.EthStatsResponse
import com.etasdemir.ethinspector.data.remote.entity.etherscan.EtherscanResponse
import com.etasdemir.ethinspector.data.remote.entity.etherscan.TransactionResponse
import com.etasdemir.ethinspector.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val ethStatsDao: EthStatsDao,
    private val addressDao: AddressDao,
    private val blockDao: BlockDao,
    private val transactionDao: TransactionDao,
) {

    suspend fun getEthStats(): Flow<BlockchairResponse<EthStatsResponse>> {
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

    suspend fun search(searchText: String): Pair<SearchType, Any> {
        when (parseRawTextToType(searchText)) {
            SearchType.TRANSACTION -> {
                val transactionResult = this.getTransactionByHash(searchText)
                return Pair(SearchType.TRANSACTION, transactionResult)
            }
            SearchType.ADDRESS -> {
                val addressResult = this.getAddressInfoByHash(searchText)
                return Pair(SearchType.ADDRESS, Any())
            }
            SearchType.BLOCK -> {
                val blockResult = this.getBlockInfoByNumber(searchText)
                return Pair(SearchType.BLOCK, Any())
            }
            else -> {
                return Pair(SearchType.INVALID, Any())
            }
        }
    }

    suspend fun getTransactionByHash(transactionId: String): EtherscanResponse<TransactionResponse> {
        return transactionDao.getTransactionByHash(transactionId)
    }

    suspend fun getAddressInfoByHash(addressId: String) {
        val addressResult = addressDao.getAddressInfoByHash(addressId)
    }

    suspend fun getBlockInfoByNumber(blockNumber: String) {
        val blockResult = blockDao.getBlockInfoByNumber(blockNumber)
    }


    private fun parseRawTextToType(text: String): SearchType {
        if (text.startsWith("0x")) {
            if (text.length == Constants.TRANSACTION_HEX_LEN) {
                return SearchType.TRANSACTION
            } else if (text.length == Constants.ADDRESS_HEX_LEN) {
                return SearchType.ADDRESS
            }
        } else {
            return SearchType.BLOCK
        }
        return SearchType.INVALID
    }
}