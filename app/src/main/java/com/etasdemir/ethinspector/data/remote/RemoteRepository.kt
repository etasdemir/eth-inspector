package com.etasdemir.ethinspector.data.remote

import com.etasdemir.ethinspector.data.ResponseResult
import com.etasdemir.ethinspector.data.domain_model.SearchType
import com.etasdemir.ethinspector.data.remote.dto.blockchair.*
import com.etasdemir.ethinspector.data.remote.dto.etherscan.*
import com.etasdemir.ethinspector.data.remote.service.*
import com.etasdemir.ethinspector.data.retrofitResponseResultFactory
import com.etasdemir.ethinspector.utils.Constants
import com.etasdemir.ethinspector.utils.toHex
import okhttp3.ResponseBody
import retrofit2.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val ethStatsService: EthStatsService,
    private val etherscanAddressService: EtherscanAddressService,
    private val blockchairAddressService: BlockchairAddressService,
    private val blockService: BlockService,
    private val transactionService: TransactionService,
) {

    suspend fun getEthStats(): ResponseResult<BlockchairResponse<EthStatsResponse>> {
        return retrofitResponseResultFactory { ethStatsService.getEthStats() }
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
        return retrofitResponseResultFactory { transactionService.getTransactionByHash(transactionHash) }
    }

    suspend fun getAccountInfoByHash(addressHash: String): ResponseResult<BlockchairAccountResponse> {
        val accountInfo = blockchairAddressService.getAccountInfoByHash(addressHash)
        val result = retrofitResponseResultFactory<ResponseBody>({ body ->
            CustomResponseParser.parseBlockchairAddressResponse(body)
        }, { accountInfo })
        return CustomResponseParser.addressJsonConverter(result)
    }

    suspend fun getContractInfoByHash(addressHash: String): ResponseResult<BlockchairContractResponse> {
        val contractInfo = blockchairAddressService.getContractInfoByHash(addressHash)
        val result = retrofitResponseResultFactory<ResponseBody>({ body ->
            CustomResponseParser.parseBlockchairAddressResponse(body)
        }, { contractInfo })
        return CustomResponseParser.addressJsonConverter(result)
    }

    suspend fun getERC20TokenTransfers(addressHash: String): ResponseResult<EtherscanTokenTransfers> {
        return retrofitResponseResultFactory { etherscanAddressService.getERC20TokenTransfers(addressHash) }
    }

    suspend fun getBlockInfoByNumber(
        blockNumber: ULong,
        getTransactionsAsObject: Boolean
    ): ResponseResult<EtherscanRPCResponse<BlockResponse>> {
        val blockHash = blockNumber.toHex()
        return retrofitResponseResultFactory {
            blockService.getBlockInfoByHash(
                blockHash,
                getTransactionsAsObject
            )
        }
    }

    @Throws(IllegalStateException::class)
    private suspend fun isAddressContract(addressHash: String): Boolean {
        val contractCreation =
            retrofitResponseResultFactory<EtherscanResponse<ContractCreationResponse>> {
                etherscanAddressService.getContractCreation(
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