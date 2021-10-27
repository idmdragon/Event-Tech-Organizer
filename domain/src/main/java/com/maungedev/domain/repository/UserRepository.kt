package com.maungedev.domain.repository

import com.maungedev.domain.model.User
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(): Flow<Resource<User>>
    fun getCurrentUserId(): String
    fun getUser(id: String): Flow<Resource<User>>
    fun updateUsername(username: String):Flow<Resource<Unit>>
    fun logout(): Unit
}