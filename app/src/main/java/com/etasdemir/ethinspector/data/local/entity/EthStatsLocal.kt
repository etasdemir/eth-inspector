package com.etasdemir.ethinspector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EthStatsLocal(
    @PrimaryKey val uid: Int,
)