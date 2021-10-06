package com.maungedev.data.mapper

import com.maungedev.data.source.local.entity.UserEntity
import com.maungedev.data.source.remote.response.UserResponse

fun UserResponse.toEntity(): UserEntity =
    UserEntity(uid,username,email,schedule,favorite)