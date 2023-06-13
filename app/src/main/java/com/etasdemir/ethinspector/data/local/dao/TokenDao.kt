package com.etasdemir.ethinspector.data.local.dao

import androidx.room.*
import com.etasdemir.ethinspector.data.local.entity.AddressTokenEntity
import com.etasdemir.ethinspector.data.local.entity.TokenTransferEntity

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTokenTransfers(tokenTransfers: List<TokenTransferEntity>)

    @Query("SELECT * from token_transfer WHERE `from`=:address OR `to`=:address")
    suspend fun getTokenTransfersByAddress(address: String): List<TokenTransferEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAddressTokens(tokens: List<AddressTokenEntity>)
}