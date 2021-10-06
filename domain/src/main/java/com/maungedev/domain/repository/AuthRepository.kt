package com.maungedev.domain.repository

import com.maungedev.domain.model.User
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signUpUser(email: String, password: String, user: User): Flow<Resource<Unit>>
    fun signInUser(email: String, password: String): Flow<Resource<Unit>>
}