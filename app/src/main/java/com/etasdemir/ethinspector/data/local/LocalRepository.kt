package com.etasdemir.ethinspector.data.local

import com.etasdemir.ethinspector.data.domain_model.*
import com.etasdemir.ethinspector.data.local.dao.*
import com.etasdemir.ethinspector.data.local.entity.*
import com.etasdemir.ethinspector.utils.Installation
import javax.inject.Inject
import javax.inject.Singleton

// TODO Saving relation operations are not atomic.

@Singleton
class LocalRepository @Inject constructor(
    private val ethStatsDao: EthStatsDao,
    private val blockDao: BlockDao,
    private val transactionDao: TransactionDao,
    private val tokenDao: TokenDao,
    private val addressDao: AddressDao,
    private val userDao: UserDao,
    private val installation: Installation
) {

    suspend fun getUser(installationId: String): UserEntity? = userDao.getUser(installationId)

    suspend fun saveUser(userEntity: UserEntity) = userDao.saveUser(userEntity)

    suspend fun saveEthStats(ethStats: EthStatsEntity) {
        ethStatsDao.saveEthStats(ethStats)
    }

    suspend fun getEthStats() = ethStatsDao.getEthStats()

    suspend fun saveBlock(block: BlockAndTransactionsRelationEntity) {
        blockDao.saveBlock(block.blockEntity)
        blockDao.saveBlockTransactions(block.transactions)
    }

    suspend fun getBlockByNumber(blockNumber: Long) =
        blockDao.getBlockByNumber(blockNumber)

    suspend fun saveTransaction(transactionEntity: TransactionEntity) {
        transactionDao.saveTransaction(transactionEntity)
    }

    suspend fun getTransactionByHash(hash: String) =
        transactionDao.getTransactionByHash(hash)

    suspend fun saveTokenTransfers(tokenTransfers: List<TokenTransferEntity>) {
        tokenDao.saveTokenTransfers(tokenTransfers)
    }

    @SuppressWarnings("WeakerAccess")
    suspend fun saveAddressTokens(tokens: List<AddressTokenEntity>) =
        tokenDao.saveAddressTokens(tokens)

    suspend fun getTokenTransfersByAddress(address: String) =
        tokenDao.getTokenTransfersByAddress(address)

    suspend fun saveContractRelation(contract: ContractAndTransactionsRelationEntity) {
        addressDao.saveContract(contract.contractInfoEntity)
        addressDao.saveAddressTransactions(contract.transactions)
    }

    suspend fun getContractRelationByAddress(address: String) =
        addressDao.getContractRelationByAddress(address)

    suspend fun getAccountRelationByAddress(address: String) =
        addressDao.getAccountRelationByAddress(address)

    suspend fun saveAccountRelation(account: AccountRelationEntity) {
        addressDao.saveAccountInfo(account.accountInfo)
        addressDao.saveAddressTransactions(account.transactions)
        addressDao.saveTransfers(account.transfers)
        this.saveAddressTokens(account.tokens)
    }

    suspend fun getFavouriteContractRelations(isFavourite: Boolean = true) =
        addressDao.getFavouriteContractRelations(isFavourite)

    suspend fun getFavouriteAccountRelations(isFavourite: Boolean = true) =
        addressDao.getFavouriteAccountRelations(isFavourite)

    suspend fun getFavouriteTransactions(): List<TransactionEntity> =
        transactionDao.getFavouriteTransactions()

    suspend fun getFavouriteBlocks(): List<BlockAndTransactionsRelationEntity> =
        blockDao.getFavouriteBlocks()
}