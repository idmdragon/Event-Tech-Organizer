package com.maungedev.domain.usecase

import com.maungedev.domain.model.Event
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import android.net.Uri
import com.maungedev.domain.model.CompetitionCategory
import com.maungedev.domain.model.ConferenceCategory
import com.maungedev.domain.model.User

interface EventUseCase {
    fun addEvent(event: Event, imageUri: Uri):Flow<Resource<Unit>>
    fun updateEvent(event: Event): Flow<Resource<Unit>>
    fun getCurrentUser(): Flow<Resource<User>>
    fun getConferenceCategory(): Flow<Resource<List<ConferenceCategory>>>
    fun getCompetitionCategory(): Flow<Resource<List<CompetitionCategory>>>
    fun getMyEvents(ids: List<String>): Flow<Resource<List<Event>>>
    fun getEventById(id: String): Flow<Resource<Event>>
    fun deleteEvent(id: String): Flow<Resource<Unit>>
    fun refreshUser(): Flow<Resource<Unit>>
}