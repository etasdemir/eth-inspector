package com.etasdemir.ethinspector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserLocal(
    @PrimaryKey val installationId: String,
    val theme: String,
    val language: String
)