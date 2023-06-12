package com.etasdemir.ethinspector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user")
data class UserEntity(
    @PrimaryKey val installationId: String,
    val theme: String,
    val language: String
)