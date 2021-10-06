package com.maungedev.domain.usecase

import com.maungedev.domain.model.User
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    fun signUpUser(email: String, password: String, user: User): Flow<Resource<Unit>>
    fun signInUser(email: String, password: String): Flow<Resource<Unit>>
}