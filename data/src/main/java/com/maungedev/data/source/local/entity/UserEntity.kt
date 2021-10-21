package com.maungedev.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey
    val uid: String,
    val username: String,
    val email: String,
    val myEvent: List<String>? = listOf(),
) {
}