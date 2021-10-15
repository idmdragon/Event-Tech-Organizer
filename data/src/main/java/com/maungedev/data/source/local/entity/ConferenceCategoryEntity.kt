package com.maungedev.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Conference_Category")
data class ConferenceCategoryEntity(
    @PrimaryKey
    val id: String,
    val categoryName: String,
)