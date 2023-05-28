package com.etasdemir.ethinspector.data.remote

import com.etasdemir.ethinspector.data.ResponseResult
import com.etasdemir.ethinspector.data.remote.dao.*
import com.etasdemir.ethinspector.data.remote.entity.SearchType
import com.etasdemir.ethinspector.data.remote.entity.blockchair.BlockchairResponse
import com.etasdemir.ethinspector.data.remote.entity.blockchair.EthStatsResponse
import com.etasdemir.ethinspector.data.remote.entity.etherscan.*
import com.etasdemir.ethinspector.data.retrofitResponseResultFactory
import com.etasdemir.ethinspector.utils.Constants
import com.etasdemir.ethinspector.utils.toHex
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val ethStatsDao: EthStatsDao,
    private val addressDao: AddressDao,
    private val blockDao: BlockDao,
    private val transactionDao: TransactionDao,
) {

    suspend fun getEthStats(): ResponseResult<BlockchairResponse<EthStatsResponse>> {
        return retrofitResponseResultFactory { ethStatsDao.getEthStats() }
    }

    suspend fun search(searchText: String): Pair<SearchType, ResponseResult<*>> {
        when (parseRawTextToType(searchText)) {
            SearchType.TRANSACTION -> {
                val transactionResponse = this.getTransactionByHash(searchText)
                return Pair(SearchType.TRANSACTION, transactionResponse)
            }

            SearchType.ADDRESS -> {
                // TODO is address account or contract
//                response = this.getAccountInfoByHash(searchText)
//                searchType = SearchType.ACCOUNT
            }

            SearchType.BLOCK -> {
                return try {
                    val convertedBlockNumber = searchText.toULong()
                    val blockResponse = this.getBlockInfoByNumber(convertedBlockNumber, true)
                    Pair(SearchType.BLOCK, blockResponse)
                } catch (error: NumberFormatException) {
                    Pair(
                        SearchType.INVALID,
                        ResponseResult.Error<Any>("NumberFormatException: Invalid block number")
                    )
                }

            }

            else -> return Pair(
                SearchType.INVALID,
                ResponseResult.Error<Any>("Invalid search type")
            )
        }
        return Pair(
            SearchType.INVALID,
            ResponseResult.Error<Any>("Unknown error at: RemoteRepository::search")
        )
    }

    suspend fun getTransactionByHash(transactionId: String): ResponseResult<EtherscanResponse<TransactionResponse>> {
        return retrofitResponseResultFactory { transactionDao.getTransactionByHash(transactionId) }
    }

    suspend fun getAccountInfoByHash(addressId: String): ResponseResult<Any> {
        return retrofitResponseResultFactory { addressDao.getAccountInfoByHash(addressId) }
    }

    suspend fun getBlockInfoByNumber(
        blockNumber: ULong,
        getTransactionsAsObject: Boolean
    ): ResponseResult<EtherscanResponse<BlockResponse>> {
        val blockHash = blockNumber.toHex()
        return retrofitResponseResultFactory {
            blockDao.getBlockInfoByHash(
                blockHash,
                getTransactionsAsObject
            )
        }
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