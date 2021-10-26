package com.maungedev.domain.usecase

import com.maungedev.domain.model.User
import com.maungedev.domain.repository.AuthRepository
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class AuthInteractor(private val authRepository: AuthRepository) : AuthUseCase {
    override fun signUpUser(email: String, password: String, user: User): Flow<Resource<Unit>> =
        authRepository.signUpUser(email,password,user)

    override fun signInUser(email: String, password: String): Flow<Resource<Unit>> =
        authRepository.signInUser(email,password)

    override fun resetPassword(email: String): Flow<Resource<Unit>> =
        authRepository.resetPassword(email)
}