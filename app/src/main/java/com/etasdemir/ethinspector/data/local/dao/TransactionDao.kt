package com.etasdemir.ethinspector.data.local.dao

import androidx.room.*
import com.etasdemir.ethinspector.data.local.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransaction(transaction: TransactionEntity)

    @Query("SELECT * from `transaction` where transactionHash=:hash")
    suspend fun getTransactionByHash(hash: String): TransactionEntity

}