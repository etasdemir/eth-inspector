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
        TransferEntity::class
    ],
    version = Constants.LOCAL_DB_VERSION
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun addressDaoLocal(): AddressDao
    abstract fun blockDaoLocal(): BlockDao
    abstract fun ethStatsDaoLocal(): EthStatsDao
    abstract fun transactionDaoLocal(): TransactionDao
    abstract fun userDaoLocal(): UserDao
}