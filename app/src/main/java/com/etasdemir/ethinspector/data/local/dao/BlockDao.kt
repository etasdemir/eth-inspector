package com.etasdemir.ethinspector.data.local.dao

import androidx.room.*
import com.etasdemir.ethinspector.data.local.entity.*

@Dao
interface BlockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBlock(blockEntity: BlockEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBlockTransactions(blockTransactionEntity: List<BlockTransactionEntity>)

    @Transaction
    @Query("SELECT * FROM block WHERE blockNumber=:blockNumber")
    suspend fun getBlockByNumber(blockNumber: Long): BlockAndTransactionsRelationEntity?

    @Transaction
    @Query("SELECT * FROM block WHERE isFavourite = 1")
    suspend fun getFavouriteBlocks(): List<BlockAndTransactionsRelationEntity>

}