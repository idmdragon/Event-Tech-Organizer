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
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class EventRepositoryImpl(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : EventRepository {

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

    override fun addEvent(event: Event, imageUri: Uri): Flow<Resource<Unit>> =
        object : NetworkBoundRequest<EventResponse>() {

            override suspend fun createCall(): Flow<FirebaseResponse<EventResponse>> =
                remote.insertEvent(event, imageUri)

            override suspend fun saveCallResult(data: EventResponse) =
                local.insertEvent(data.toEntity())

        }.asFlow()

    override fun getConferenceCategory(): Flow<Resource<List<ConferenceCategory>>> =
        object :NetworkBoundResource<List<ConferenceCategory>,List<ConferenceCategoryResponse>>(){
            override fun loadFromDB(): Flow<List<ConferenceCategory>?> =
                local.selectAllConferenceCategory().toConferenceCategoryListFlowModel()

            override fun shouldFetch(data: List<ConferenceCategory>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<FirebaseResponse<List<ConferenceCategoryResponse>>> =
                remote.getAllConferenceCategory()

            override suspend fun saveCallResult(data: List<ConferenceCategoryResponse>) =
                local.insertConferenceCategory(data.toListConferenceCategoryEntity())
        }.asFlow()

    override fun getCompetitionCategory(): Flow<Resource<List<CompetitionCategory>>> =
        object :NetworkBoundResource<List<CompetitionCategory>,List<CompetitionCategoryResponse>>(){
            override fun loadFromDB(): Flow<List<CompetitionCategory>?> =
                local.selectAllCompetitionCategory().toCompetitionCategoryListFlowModel()

            override fun shouldFetch(data: List<CompetitionCategory>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<FirebaseResponse<List<CompetitionCategoryResponse>>> =
                remote.getAllCompetitionCategory()

            override suspend fun saveCallResult(data: List<CompetitionCategoryResponse>) =
                local.insertCompetitionCategory(data.toListCompetitionCategoryEntity())
        }.asFlow()
}