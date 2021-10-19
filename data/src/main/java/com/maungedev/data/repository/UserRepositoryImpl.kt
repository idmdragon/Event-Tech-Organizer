package com.maungedev.data.repository

import android.net.Uri
import com.maungedev.data.helper.NetworkBoundRequest
import com.maungedev.data.helper.NetworkBoundResource
import com.maungedev.data.mapper.*
import com.maungedev.data.source.local.LocalDataSource
import com.maungedev.data.source.remote.FirebaseResponse
import com.maungedev.data.source.remote.RemoteDataSource
import com.maungedev.data.source.remote.response.CompetitionCategoryResponse
import com.maungedev.data.source.remote.response.ConferenceCategoryResponse
import com.maungedev.data.source.remote.response.EventResponse
import com.maungedev.data.source.remote.response.UserResponse
import com.maungedev.domain.model.CompetitionCategory
import com.maungedev.domain.model.ConferenceCategory
import com.maungedev.domain.model.Event
import com.maungedev.domain.model.User
import com.maungedev.domain.repository.EventRepository
import com.maungedev.domain.repository.UserRepository
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : UserRepository {

    override fun getCurrentUser(): Flow<Resource<User>> =
        object : NetworkBoundResource<User, UserResponse>() {
            override fun loadFromDB(): Flow<User?> =
                local.selectUser().toFlowModel()

            override fun shouldFetch(data: User?): Boolean =
                data == null

            override suspend fun createCall(): Flow<FirebaseResponse<UserResponse>> =
                remote.getCurrentUser()

            override suspend fun saveCallResult(data: UserResponse) =
                local.insertUser(data.toEntity())
        }.asFlow()

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
