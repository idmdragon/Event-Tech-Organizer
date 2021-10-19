package com.maungedev.data.repository

import com.maungedev.data.helper.NetworkBoundResource
import com.maungedev.data.mapper.*
import com.maungedev.data.source.local.LocalDataSource
import com.maungedev.data.source.remote.FirebaseResponse
import com.maungedev.data.source.remote.RemoteDataSource
import com.maungedev.data.source.remote.response.UserResponse
import com.maungedev.domain.model.User
import com.maungedev.domain.repository.UserRepository
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : UserRepository {
    override fun getCurrentUserId(): String =
        remote.getCurrentUserId()

    override fun getUser(id: String): Flow<Resource<User>> =
        object : NetworkBoundResource<User, UserResponse>() {
            override fun loadFromDB(): Flow<User?> =
                local.selectUser().toFlowModel()

            override fun shouldFetch(data: User?): Boolean =
                data == null

            override suspend fun createCall(): Flow<FirebaseResponse<UserResponse>> =
                remote.getCurrentUser(id)

            override suspend fun saveCallResult(data: UserResponse) =
                local.insertUser(data.toEntity())
        }.asFlow()

    override fun getCurrentUser(): Flow<Resource<User>> =
        flow{
            val userId = getCurrentUserId()
            if (userId.isNotEmpty()){
                emitAll(getUser(userId))
            }
        }

    override fun updateUsername(username: String): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override fun resetPassword(email: String): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override fun logout(): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }


}
