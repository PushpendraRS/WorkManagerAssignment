package com.example.workmanagerassignment.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val body: String,
    val title: String,
    val userId: Int
)