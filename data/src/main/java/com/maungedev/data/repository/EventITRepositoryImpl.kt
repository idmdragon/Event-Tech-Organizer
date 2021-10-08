package com.maungedev.data.repository

import android.net.Uri
import com.maungedev.data.helper.NetworkBoundRequest
import com.maungedev.data.mapper.toEntity
import com.maungedev.data.source.local.LocalDataSource
import com.maungedev.data.source.remote.FirebaseResponse
import com.maungedev.data.source.remote.RemoteDataSource
import com.maungedev.data.source.remote.response.EventResponse
import com.maungedev.domain.model.Event
import com.maungedev.domain.repository.EventITRepository
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class EventITRepositoryImpl(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : EventITRepository {
    override fun addEvent(event: Event, imageUri: Uri): Flow<Resource<Unit>> =
        object : NetworkBoundRequest<EventResponse>() {

            override suspend fun createCall(): Flow<FirebaseResponse<EventResponse>> =
                remote.insertEvent(event, imageUri)

            override suspend fun saveCallResult(data: EventResponse) =
                local.insertEvent(data.toEntity())

        }.asFlow()
}