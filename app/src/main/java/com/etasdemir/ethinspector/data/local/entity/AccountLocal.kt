package com.etasdemir.ethinspector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountLocal(
    @PrimaryKey val uid: Int,
)