package com.etasdemir.ethinspector.data

import com.etasdemir.ethinspector.data.cache.LocalFirstStrategy
import com.etasdemir.ethinspector.data.cache.RemoteFirstStrategy
import com.etasdemir.ethinspector.data.domain_model.*
import com.etasdemir.ethinspector.data.local.LocalRepository
import com.etasdemir.ethinspector.data.remote.RemoteRepository
import com.etasdemir.ethinspector.data.remote.dto.etherscan.*
import com.etasdemir.ethinspector.data.remote.service.BlockchairAccountResponse
import com.etasdemir.ethinspector.data.remote.service.BlockchairContractResponse
import com.etasdemir.ethinspector.mappers.domain_to_local.*
import com.etasdemir.ethinspector.mappers.local_to_domain.*
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

    private val installationId = installation.id()

    suspend fun getUser(): User {
        val userEntity = localRepository.getUser()
        return User(installation.id(), AvailableThemes.Light, AvailableLanguages.English)
    }

    suspend fun updateUser(newUser: User) {
        // update or create user
    }

    suspend fun saveBlock(block: Block) {
        val blockEntity = mapBlockToBlockEntity(block)
        localRepository.saveBlock(blockEntity)
    }

    suspend fun saveTransaction(transaction: Transaction) {
        val transactionEntity = mapTransactionToTransactionEntity(transaction)
        localRepository.saveTransaction(transactionEntity)
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun search(searchText: String): Pair<SearchType, ResponseResult<*>> {
        val response = remoteRepository.search(searchText)
        when (response.first) {
            SearchType.TRANSACTION -> {
                val transaction =
                    mapTransactionResponseToTransaction(
                        response.second as ResponseResult<EtherscanRPCResponse<TransactionResponse>>
                    )
                transaction.data?.let {
                    localRepository.saveTransaction(mapTransactionToTransactionEntity(transaction.data))
                }
                return Pair(response.first, transaction)
            }

            SearchType.BLOCK -> {
                val block =
                    mapBlockResponseToBlock(response.second as ResponseResult<EtherscanRPCResponse<BlockResponse>>)
                block.data?.let {
                    localRepository.saveBlock(mapBlockToBlockEntity(it))
                }
                return Pair(response.first, block)
            }

            SearchType.ACCOUNT -> {
                val account =
                    mapAccountResponseToAccount(
                        response.second as ResponseResult<BlockchairAccountResponse>,
                        searchText
                    )
                return Pair(response.first, account)
            }

            SearchType.CONTRACT -> {
                val contract =
                    mapContractResponseToContract(
                        response.second as ResponseResult<BlockchairContractResponse>,
                        searchText
                    )
                contract.data?.let {
                    localRepository.saveContractRelation(mapContractToContractRelation(contract.data))
                }
                return Pair(response.first, contract)
            }

            else -> {
                return response
            }
        }
    }

    suspend fun getEthStats(): ResponseResult<EthStats> {
        val cacheStrategy =
            RemoteFirstStrategy(
                ::mapEthStatsResponseToEthStats,
                ::mapEthStatsEntityToEthStats
            ) {
                mapEthStatsToEntity(it, installationId)
            }
        return cacheStrategy.execute(
            remoteRepository::getEthStats,
            localRepository::getEthStats,
            localRepository::saveEthStats
        )
    }

    suspend fun getBlockInfoByNumber(
        blockNumber: ULong, getTransactionsAsObject: Boolean
    ): ResponseResult<Block> {
        val cacheStrategy = LocalFirstStrategy(
            ::mapBlockResponseToBlock,
            ::mapBlockEntityToBlock,
            ::mapBlockToBlockEntity
        )
        return cacheStrategy.execute(
            {
                remoteRepository.getBlockInfoByNumber(blockNumber, getTransactionsAsObject)
            },
            {
                localRepository.getBlockByNumber(blockNumber.toLong())
            },
            localRepository::saveBlock
        )
    }

    suspend fun getTransactionByHash(
        transactionHash: String
    ): ResponseResult<Transaction> {
        val cacheStrategy = LocalFirstStrategy(
            ::mapTransactionResponseToTransaction,
            ::mapTransactionEntityToTransaction,
            ::mapTransactionToTransactionEntity
        )
        return cacheStrategy.execute(
            { remoteRepository.getTransactionByHash(transactionHash) },
            { localRepository.getTransactionByHash(transactionHash) },
            localRepository::saveTransaction
        )
    }

    suspend fun getAccountInfoByHash(addressHash: String): ResponseResult<Account> {
        val cacheStrategy = LocalFirstStrategy(
            { mapAccountResponseToAccount(it, addressHash) },
            ::mapAccountRelationToAccount,
            { mapAccountToAccountRelation(it, addressHash) }
        )
        val account = cacheStrategy.execute(
            { remoteRepository.getAccountInfoByHash(addressHash) },
            { localRepository.getAccountRelationByAddress(addressHash) },
            localRepository::saveAccountRelation
        )
        if (account.data != null && account.data.addressTokens.isEmpty()) {
            val tokens = this.getERC20TokenTransfers(addressHash)
            addTransfersToAccount(account, tokens)
        }
        return account
    }

    suspend fun getContractInfoByHash(addressHash: String): ResponseResult<Contract> {
        val cacheStrategy = LocalFirstStrategy(
            { mapContractResponseToContract(it, addressHash) },
            ::mapContractRelationToContract,
            ::mapContractToContractRelation,
        )
        return cacheStrategy.execute(
            { remoteRepository.getContractInfoByHash(addressHash) },
            { localRepository.getContractRelationByAddress(addressHash) },
            localRepository::saveContractRelation
        )
    }

    @SuppressWarnings("WeakerAccess")
    suspend fun getERC20TokenTransfers(addressHash: String): ResponseResult<List<TokenTransfer>> {
        val cacheStrategy = LocalFirstStrategy(
            ::mapTokenTransfersResponseToTokenTransfers,
            ::mapTokenTransferEntityToDomain,
            ::mapTokenTransfersToEntity
        )
        return cacheStrategy.execute(
            { remoteRepository.getERC20TokenTransfers(addressHash) },
            { localRepository.getTokenTransfersByAddress(addressHash) },
            localRepository::saveTokenTransfers
        )
    }
}