package com.maungedev.data.mapper

import com.maungedev.data.source.local.entity.UserEntity
import com.maungedev.data.source.remote.response.UserResponse
import com.maungedev.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun UserResponse.toEntity(): UserEntity =
    UserEntity(uid,username,email, myEvent)

fun UserEntity.toModel(): User =
    User(uid, username, email,myEvent)

fun Flow<UserEntity>.toFlowModel(): Flow<User> =
    this.map {
        it.toModel()
    }