package com.etasdemir.ethinspector.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.etasdemir.ethinspector.data.local.dao.*
import com.etasdemir.ethinspector.data.local.entity.*
import com.etasdemir.ethinspector.utils.Constants

@Database(
    entities = [
        AccountInfoEntity::class,
        BlockEntity::class,
        BlockTransactionEntity::class,
        ContractInfoEntity::class,
        EthStatsEntity::class,
        TransactionEntity::class,
        UserEntity::class,
        AddressTransactionEntity::class,
        TokenEntityAccountCrossRef::class,
        TokenEntity::class,
        TransferEntity::class,
        TokenTransferEntity::class
    ],
    version = Constants.LOCAL_DB_VERSION
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao
    abstract fun blockDao(): BlockDao
    abstract fun ethStatsDao(): EthStatsDao
    abstract fun transactionDao(): TransactionDao
    abstract fun userDao(): UserDao
    abstract fun tokenTransferDao(): TokenTransferDao
}