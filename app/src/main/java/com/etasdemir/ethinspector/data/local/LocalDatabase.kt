package com.etasdemir.ethinspector.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.etasdemir.ethinspector.data.local.dao.*
import com.etasdemir.ethinspector.data.local.entity.*
import com.etasdemir.ethinspector.utils.Constants

@Database(
    entities = [
        AccountInfoLocal::class,
        BlockLocal::class,
        BlockTransactionItemLocal::class,
        ContractInfoLocal::class,
        EthStatsLocal::class,
        TransactionLocal::class,
        UserLocal::class,
        AddressTransactionItemLocal::class,
        TokenItemAccountCrossRef::class,
        TokenItemLocal::class,
        TransferItemLocal::class
    ],
    version = Constants.LOCAL_DB_VERSION
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun addressDaoLocal(): AddressDaoLocal
    abstract fun blockDaoLocal(): BlockDaoLocal
    abstract fun ethStatsDaoLocal(): EthStatsDaoLocal
    abstract fun transactionDaoLocal(): TransactionDaoLocal
    abstract fun userDaoLocal(): UserDaoLocal
}