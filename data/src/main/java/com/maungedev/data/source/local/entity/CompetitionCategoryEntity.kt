package com.maungedev.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Competition_Category")
data class CompetitionCategoryEntity(
    @PrimaryKey
    val id: String,
    val categoryName: String,
)