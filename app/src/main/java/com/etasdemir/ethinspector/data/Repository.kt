package com.etasdemir.ethinspector.data

import com.etasdemir.ethinspector.data.domain_model.*
import com.etasdemir.ethinspector.data.local.LocalRepository
import com.etasdemir.ethinspector.data.remote.RemoteRepository
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

    suspend fun search(searchText: String): Pair<SearchType, ResponseResult<*>> {
        val response = remoteRepository.search(searchText)
        when (response.first) {
            SearchType.TRANSACTION -> {
                // response.second => TransactionResponse to Transaction
            }
            SearchType.BLOCK -> {
                // response.second => BlockResponse to Block
            }
            SearchType.ACCOUNT -> {
                // response.second => AccountResponse to Account
            }
            SearchType.CONTRACT -> {
                // response.second => ContractResponse to Contract
            }
            else -> {
                return response
            }
        }
    }

    suspend fun getEthStats(): ResponseResult<EthStats> {
        val response = remoteRepository.getEthStats()
        // map response data to domain eth stats
//        return if (response is ResponseResult.Success) {
//            // save
//            response
//        } else {
//            // retrieve and return
//        }
    }

    suspend fun getBlockInfoByNumber(
        blockNumber: ULong,
        getTransactionsAsObject: Boolean
    ): ResponseResult<Block> {
        return remoteRepository.getBlockInfoByNumber(blockNumber, getTransactionsAsObject)
    }

    suspend fun getTransactionByHash(
        transactionHash: String
    ): ResponseResult<Transaction> {
        return remoteRepository.getTransactionByHash(transactionHash)
    }

    suspend fun getAccountInfoByHash(addressHash: String): ResponseResult<Account> {
        return remoteRepository.getAccountInfoByHash(addressHash)
    }

    suspend fun getContractInfoByHash(addressHash: String): ResponseResult<Contract> {
        return remoteRepository.getContractInfoByHash(addressHash)
    }

    suspend fun getERC20TokenTransfers(addressHash: String): ResponseResult<TokenTransfer> {
        return remoteRepository.getERC20TokenTransfers(addressHash)
    }
}