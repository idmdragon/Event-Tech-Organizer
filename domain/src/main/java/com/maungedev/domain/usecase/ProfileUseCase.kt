package com.maungedev.domain.usecase

import com.maungedev.domain.model.User
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileUseCase {
//    fun getCurrentUser(): Flow<Resource<User>>
    fun updateUsername(username: String): Flow<Resource<Unit>>
    fun resetPassword(email: String): Flow<Resource<Unit>>
    fun logout(): Flow<Resource<Unit>>
}