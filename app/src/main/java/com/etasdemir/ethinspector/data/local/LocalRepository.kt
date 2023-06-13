package com.etasdemir.ethinspector.data.local

import com.etasdemir.ethinspector.data.domain_model.*
import com.etasdemir.ethinspector.data.local.dao.*
import com.etasdemir.ethinspector.data.local.entity.*
import com.etasdemir.ethinspector.utils.Installation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val ethStatsDao: EthStatsDao,
    private val blockDao: BlockDao,
    private val transactionDao: TransactionDao,
    private val tokenTransferDao: TokenTransferDao,
    private val addressDao: AddressDao,
    private val userDao: UserDao,
    private val installation: Installation
) {

    suspend fun getUser(): UserEntity {
        // TODO if user table empty create and return
        return UserEntity(
            installation.id(),
            AvailableThemes.Light.name,
            AvailableLanguages.English.name
        )
    }

    suspend fun saveUser(newUser: User) {

    }

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
        tokenTransferDao.saveTokenTransfers(tokenTransfers)
    }

    suspend fun getTokenTransfersByAddress(address: String) =
        tokenTransferDao.getTokenTransfersByAddress(address)
}