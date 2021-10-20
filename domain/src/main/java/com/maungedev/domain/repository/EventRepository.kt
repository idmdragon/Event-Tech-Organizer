package com.maungedev.domain.repository

import android.net.Uri
import com.maungedev.domain.model.CompetitionCategory
import com.maungedev.domain.model.ConferenceCategory
import com.maungedev.domain.model.Event
import com.maungedev.domain.model.User
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun addEvent(event: Event, imageUri: Uri): Flow<Resource<Unit>>
    fun getConferenceCategory(): Flow<Resource<List<ConferenceCategory>>>
    fun getCompetitionCategory(): Flow<Resource<List<CompetitionCategory>>>
    fun getMyEvents(ids: List<String>): Flow<Resource<List<Event>>>
    fun getCurrentUser(): Flow<Resource<User>>
    fun getCurrentUserId(): String
    fun getUser(id: String): Flow<Resource<User>>
}