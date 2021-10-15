package com.maungedev.data.mapper

import com.maungedev.data.source.local.entity.ConferenceCategoryEntity
import com.maungedev.data.source.local.entity.UserEntity
import com.maungedev.data.source.remote.response.UserResponse
import com.maungedev.domain.model.ConferenceCategory
import com.maungedev.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun UserResponse.toEntity(): UserEntity =
    UserEntity(uid,username,email,schedule,favorite)

fun UserEntity.toModel(): User =
    User(uid, username, email)

fun Flow<UserEntity>.toFlowModel(): Flow<User> =
    this.map {
        it.toModel()
    }