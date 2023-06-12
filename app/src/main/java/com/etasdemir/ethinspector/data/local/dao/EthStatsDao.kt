package com.etasdemir.ethinspector.data.local.dao

import androidx.room.*
import com.etasdemir.ethinspector.data.local.entity.EthStatsEntity

@Dao
interface EthStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEthStats(ethStatsEntity: EthStatsEntity)

    @Query("SELECT * from eth_stats")
    suspend fun getEthStats(): EthStatsEntity?

}