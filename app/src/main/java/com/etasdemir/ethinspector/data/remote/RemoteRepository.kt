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
                return try {
                    if (isAddressContract(searchText)) {
                        val contractResponse = getContractInfoByHash(searchText)
                        Pair(SearchType.CONTRACT, contractResponse)
                    } else {
                        val accountResponse = getAccountInfoByHash(searchText)
                        Pair(SearchType.ACCOUNT, accountResponse)
                    }
                } catch (exception: IllegalStateException) {
                    Pair(
                        SearchType.ADDRESS,
                        ResponseResult.Error<Any>(
                            exception.message ?: "Unknown error at isAddressContract(searchText)"
                        )
                    )
                }
            }

            SearchType.BLOCK -> {
                val convertedBlockNumber = searchText.toULong()
                val blockResponse = this.getBlockInfoByNumber(convertedBlockNumber, true)
                return Pair(SearchType.BLOCK, blockResponse)
            }

            else -> return Pair(
                SearchType.INVALID,
                ResponseResult.Error<Any>("Invalid search type")
            )
        }
    }

    suspend fun getTransactionByHash(transactionHash: String): ResponseResult<EtherscanRPCResponse<TransactionResponse>> {
        return retrofitResponseResultFactory { transactionDao.getTransactionByHash(transactionHash) }
    }

    suspend fun getAccountInfoByHash(addressHash: String): ResponseResult<Any> {
        return retrofitResponseResultFactory { addressDao.getAccountInfoByHash(addressHash) }
    }

    suspend fun getContractInfoByHash(addressHash: String): ResponseResult<Any> {
        return retrofitResponseResultFactory { addressDao.getContractInfoByHash(addressHash) }
    }

    suspend fun getBlockInfoByNumber(
        blockNumber: ULong,
        getTransactionsAsObject: Boolean
    ): ResponseResult<EtherscanRPCResponse<BlockResponse>> {
        val blockHash = blockNumber.toHex()
        return retrofitResponseResultFactory {
            blockDao.getBlockInfoByHash(
                blockHash,
                getTransactionsAsObject
            )
        }
    }

    @Throws(IllegalStateException::class)
    private suspend fun isAddressContract(addressHash: String): Boolean {
        val contractCreation =
            retrofitResponseResultFactory<EtherscanResponse<ContractCreationResponse>> {
                addressDao.getContractCreation(
                    listOf(addressHash)
                )
            }
        if (contractCreation is ResponseResult.Success) {
            return contractCreation.data?.status == "1"
        }
        throw IllegalStateException("Exception in isAddressContract: ${contractCreation.errorMessage}")
    }

    private fun parseRawTextToType(text: String): SearchType {
        if (text.startsWith("0x")) {
            if (text.length == Constants.TRANSACTION_HEX_LEN) {
                return SearchType.TRANSACTION
            } else if (text.length == Constants.ADDRESS_HEX_LEN) {
                return SearchType.ADDRESS
            }
        } else {
            return try {
                text.toULong()
                SearchType.BLOCK
            } catch (error: NumberFormatException) {
                SearchType.INVALID
            }
        }
        return SearchType.INVALID
    }
}