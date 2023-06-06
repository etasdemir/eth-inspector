package com.etasdemir.ethinspector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BlockLocal(
    @PrimaryKey val uid: Int,
)