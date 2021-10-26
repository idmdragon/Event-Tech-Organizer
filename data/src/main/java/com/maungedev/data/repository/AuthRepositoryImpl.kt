package com.maungedev.data.repository

import com.maungedev.data.helper.NetworkBoundRequest
import com.maungedev.data.mapper.toEntity
import com.maungedev.data.source.local.LocalDataSource
import com.maungedev.data.source.remote.FirebaseResponse
import com.maungedev.data.source.remote.RemoteDataSource
import com.maungedev.data.source.remote.response.UserResponse
import com.maungedev.domain.model.User
import com.maungedev.domain.repository.AuthRepository
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : AuthRepository {
    override fun signUpUser(email: String, password: String, user: User): Flow<Resource<Unit>> =
        object : NetworkBoundRequest<UserResponse>() {

            override suspend fun createCall(): Flow<FirebaseResponse<UserResponse>> =
                remote.signUp(email, password, user)

            override suspend fun saveCallResult(data: UserResponse) =
                local.insertUser(data.toEntity())

        }.asFlow()

    override fun signInUser(email: String, password: String): Flow<Resource<Unit>> =
        object : NetworkBoundRequest<UserResponse>() {

            override suspend fun createCall(): Flow<FirebaseResponse<UserResponse>> =
                remote.signIn(email, password)

            override suspend fun saveCallResult(data: UserResponse) =
                local.insertUser(data.toEntity())

        }.asFlow()

    override fun resetPassword(email: String): Flow<Resource<Unit>> =
        flow {
            FirebaseResponse.Success(remote.resetPassword(email))
        }


}