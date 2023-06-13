package com.etasdemir.ethinspector.data.local.dao

import androidx.room.*
import com.etasdemir.ethinspector.data.local.entity.*

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveContract(contractInfoEntity: ContractInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAddressTransactions(transactions: List<AddressTransactionEntity>)

    @Transaction
    @Query("SELECT * FROM contract_info WHERE contractAddress=:contractAddress")
    suspend fun getContractRelationByAddress(contractAddress: String): ContractAndTransactionsRelationEntity?

    @Transaction
    @Query("SELECT * from account_info where accountAddress=:address")
    suspend fun getAccountRelationByAddress(address: String): AccountRelationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAccountInfo(accountInfoEntity: AccountInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransfers(addressTransfers: List<TransferEntity>)
}