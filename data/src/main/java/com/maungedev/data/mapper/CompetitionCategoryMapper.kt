package com.maungedev.data.mapper

import com.maungedev.data.source.local.entity.CompetitionCategoryEntity
import com.maungedev.data.source.local.entity.ConferenceCategoryEntity
import com.maungedev.data.source.remote.response.CompetitionCategoryResponse
import com.maungedev.data.source.remote.response.ConferenceCategoryResponse
import com.maungedev.domain.model.CompetitionCategory
import com.maungedev.domain.model.ConferenceCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun CompetitionCategoryEntity.toCompetitionCategoryModel(): CompetitionCategory =
    CompetitionCategory(
        id, categoryName
    )

fun CompetitionCategoryResponse.toCompetitionCategoryEntity(): CompetitionCategoryEntity =
    CompetitionCategoryEntity(
        uid, categoryName
    )

fun List<CompetitionCategoryEntity>.toListCompetitionCategoryModel(): List<CompetitionCategory> =
    this.map {
        it.toCompetitionCategoryModel()
    }

fun List<CompetitionCategoryResponse>.toListCompetitionCategoryEntity(): List<CompetitionCategoryEntity> =
    this.map {
        it.toCompetitionCategoryEntity()
    }

fun Flow<List<CompetitionCategoryEntity>>.toCompetitionCategoryListFlowModel():Flow<List<CompetitionCategory>> =
    this.map {
        it.toListCompetitionCategoryModel()
    }
