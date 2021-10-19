package com.maungedev.domain.usecase

import com.maungedev.domain.model.User
import com.maungedev.domain.repository.UserRepository
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow


class ProfileInteractor (private val userRepository: UserRepository): ProfileUseCase {
    override fun getCurrentUser(): Flow<Resource<User>> =
        userRepository.getCurrentUser()

    override fun updateUsername(username: String): Flow<Resource<Unit>> =
        userRepository.updateUsername(username)

    override fun resetPassword(email: String): Flow<Resource<Unit>> =
        userRepository.resetPassword(email)

    override fun logout(): Flow<Resource<Unit>> =
        userRepository.logout()
}