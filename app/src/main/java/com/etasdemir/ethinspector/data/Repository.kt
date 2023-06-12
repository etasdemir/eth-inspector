package com.etasdemir.ethinspector.data

import com.etasdemir.ethinspector.data.domain_model.*
import com.etasdemir.ethinspector.data.local.LocalRepository
import com.etasdemir.ethinspector.data.remote.RemoteRepository
import com.etasdemir.ethinspector.data.remote.dto.etherscan.*
import com.etasdemir.ethinspector.data.remote.service.BlockchairAccountResponse
import com.etasdemir.ethinspector.data.remote.service.BlockchairContractResponse
import com.etasdemir.ethinspector.mappers.remote_to_domain.*
import com.etasdemir.ethinspector.utils.Installation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val installation: Installation
) {

    suspend fun getUser(): User {
        // retrieve user
        // if user null, create initial user
        return User(installation.id(), AvailableThemes.Light, AvailableLanguages.English)
    }

    suspend fun updateUser(newUser: User) {
        // update or create user
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun search(searchText: String): Pair<SearchType, ResponseResult<*>> {
        val response = remoteRepository.search(searchText)
        when (response.first) {
            SearchType.TRANSACTION -> {
                val transaction =
                    mapTransactionResponseToTransaction(response.second as ResponseResult<EtherscanRPCResponse<TransactionResponse>>)
                return Pair(response.first, transaction)
            }

            SearchType.BLOCK -> {
                val block =
                    mapBlockResponseToBlock(response.second as ResponseResult<EtherscanRPCResponse<BlockResponse>>)
                return Pair(response.first, block)
            }

            SearchType.ACCOUNT -> {
                val account =
                    mapAccountResponseToAccount(response.second as ResponseResult<BlockchairAccountResponse>)
                return Pair(response.first, account)
            }

            SearchType.CONTRACT -> {
                val contract =
                    mapContractResponseToContract(response.second as ResponseResult<BlockchairContractResponse>)
                return Pair(response.first, contract)
            }

            else -> {
                return response
            }
        }
    }

    suspend fun getEthStats(): ResponseResult<EthStats> {
        val ethStatsResponse = remoteRepository.getEthStats()
        return mapEthStatsResponseToEthStats(ethStatsResponse)
    }

    suspend fun getBlockInfoByNumber(
        blockNumber: ULong, getTransactionsAsObject: Boolean
    ): ResponseResult<Block> {
        val blockResponse =
            remoteRepository.getBlockInfoByNumber(blockNumber, getTransactionsAsObject)
        return mapBlockResponseToBlock(blockResponse)
    }

    suspend fun getTransactionByHash(
        transactionHash: String
    ): ResponseResult<Transaction> {
        val transactionResponse = remoteRepository.getTransactionByHash(transactionHash)
        return mapTransactionResponseToTransaction(transactionResponse)
    }

    suspend fun getAccountInfoByHash(addressHash: String): ResponseResult<Account> {
        val accountResponse = remoteRepository.getAccountInfoByHash(addressHash)
        val tokenTransfers = this.getERC20TokenTransfers(addressHash)
        val account = mapAccountResponseToAccount(accountResponse)
        return addTransfersToAccount(account, tokenTransfers)
    }

    suspend fun getContractInfoByHash(addressHash: String): ResponseResult<Contract> {
        val contractResponse = remoteRepository.getContractInfoByHash(addressHash)
        return mapContractResponseToContract(contractResponse)
    }

    @SuppressWarnings("WeakerAccess")
    suspend fun getERC20TokenTransfers(addressHash: String): ResponseResult<List<TokenTransfer>> {
        val tokenTransfersResponse = remoteRepository.getERC20TokenTransfers(addressHash)
        return mapTokenTransfersResponseToTokenTransfers(tokenTransfersResponse)
    }
}