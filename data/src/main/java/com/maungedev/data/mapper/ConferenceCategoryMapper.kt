package com.maungedev.data.mapper

import com.maungedev.data.source.local.entity.ConferenceCategoryEntity
import com.maungedev.data.source.remote.response.ConferenceCategoryResponse
import com.maungedev.domain.model.ConferenceCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun ConferenceCategoryEntity.toConferenceCategoryModel(): ConferenceCategory =
    ConferenceCategory(
        id, categoryName
    )

fun ConferenceCategoryResponse.toConferenceCategoryEntity(): ConferenceCategoryEntity =
    ConferenceCategoryEntity(
        uid, categoryName
    )

fun List<ConferenceCategoryEntity>.toListConferenceCategoryModel(): List<ConferenceCategory> =
    this.map {
        it.toConferenceCategoryModel()
    }

fun List<ConferenceCategoryResponse>.toListConferenceCategoryEntity(): List<ConferenceCategoryEntity> =
    this.map {
        it.toConferenceCategoryEntity()
    }

fun Flow<List<ConferenceCategoryEntity>>.toConferenceCategoryListFlowModel():Flow<List<ConferenceCategory>> =
    this.map {
        it.toListConferenceCategoryModel()
    }
