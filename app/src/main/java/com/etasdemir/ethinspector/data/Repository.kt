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
    installation: Installation
) {

    private val installationId = installation.id()

    suspend fun getUser(): User {
        val userEntity = localRepository.getUser(installationId)
        if (userEntity == null) {
            val defaultUser = User(
                installationId,
                AvailableThemes.Dark,
                true,
                AvailableLanguages.English,
                true
            )
            this.saveUser(defaultUser)
            return defaultUser
        }
        return mapUserEntityToDomain(userEntity)!!
    }

    suspend fun saveUser(user: User) {
        val userEntity = mapUserToEntity(user)
        localRepository.saveUser(userEntity)
    }

    suspend fun saveBlock(block: Block) {
        val blockEntity = mapBlockToBlockEntity(block)
        localRepository.saveBlock(blockEntity)
    }

    suspend fun saveTransaction(transaction: Transaction) {
        val transactionEntity = mapTransactionToTransactionEntity(transaction)
        localRepository.saveTransaction(transactionEntity)
    }

    suspend fun saveAccount(account: Account) {
        val accountRelation = mapAccountToAccountRelation(account)
        localRepository.saveAccountRelation(accountRelation)
    }

    suspend fun saveContract(contract: Contract) {
        val contractRelation = mapContractToContractRelation(contract)
        localRepository.saveContractRelation(contractRelation)
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
                    this.saveTransaction(transaction.data)
                }
                return Pair(response.first, transaction)
            }

            SearchType.BLOCK -> {
                val block =
                    mapBlockResponseToBlock(response.second as ResponseResult<EtherscanRPCResponse<BlockResponse>>)
                block.data?.let {
                    this.saveBlock(block.data)
                }
                return Pair(response.first, block)
            }

            SearchType.ACCOUNT -> {
                val account =
                    mapAccountResponseToAccount(
                        response.second as ResponseResult<BlockchairAccountResponse>,
                        searchText
                    )
                account.data?.let {
                    this.saveAccount(account.data)
                }
                return Pair(response.first, account)
            }

            SearchType.CONTRACT -> {
                val contract =
                    mapContractResponseToContract(
                        response.second as ResponseResult<BlockchairContractResponse>,
                        searchText
                    )
                contract.data?.let {
                    this.saveContract(contract.data)
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
        ) { old, new ->
            val blockEntity = new.blockEntity.copy(isFavourite = old.blockEntity.isFavourite)
            new.copy(blockEntity = blockEntity)
        }
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
        ) { old, new ->
            new.copy(isFavourite = old.isFavourite)
        }
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
            ::mapAccountToAccountRelation
        ) { old, new ->
            val accountInfo = new.accountInfo.copy(
                isFavourite = old.accountInfo.isFavourite,
                userGivenName = old.accountInfo.userGivenName
            )
            new.copy(accountInfo = accountInfo)
        }
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
        ) { old, new ->
            val contractInfo = new.contractInfoEntity.copy(
                isFavourite = old.contractInfoEntity.isFavourite,
                userGivenName = old.contractInfoEntity.userGivenName
            )
            new.copy(contractInfoEntity = contractInfo)
        }
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
            ::mapTokenTransfersToEntity,
            null
        )
        return cacheStrategy.execute(
            { remoteRepository.getERC20TokenTransfers(addressHash) },
            { localRepository.getTokenTransfersByAddress(addressHash) },
            localRepository::saveTokenTransfers
        )
    }

    suspend fun getSavedAddresses(): SavedAddresses {
        val savedAccounts = localRepository.getFavouriteAccountRelations()
        val savedContracts = localRepository.getFavouriteContractRelations()
        return SavedAddresses(savedAccounts, savedContracts)
    }

    suspend fun getFavouriteTransactions(): List<Transaction> {
        val transactionEntities = localRepository.getFavouriteTransactions()
        val transactions = arrayListOf<Transaction>()
        transactionEntities.forEach {
            transactions.add(mapTransactionEntityToTransaction(it))
        }
        return transactions
    }

    suspend fun getFavouriteBlocks(): List<Block> {
        val blockEntities = localRepository.getFavouriteBlocks()
        val blocks = arrayListOf<Block>()
        blockEntities.forEach {
            blocks.add(mapBlockEntityToBlock(it))
        }
        return blocks
    }

}