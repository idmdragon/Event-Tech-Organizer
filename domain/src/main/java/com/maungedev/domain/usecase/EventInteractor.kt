package com.maungedev.domain.usecase

import com.maungedev.domain.model.Event
import com.maungedev.domain.repository.EventRepository
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import android.net.Uri
import com.maungedev.domain.model.CompetitionCategory
import com.maungedev.domain.model.ConferenceCategory
import com.maungedev.domain.model.User

class EventInteractor(private val eventRepository: EventRepository): EventUseCase {
    override fun getCurrentUser(): Flow<Resource<User>> =
        eventRepository.getCurrentUser()

    override fun addEvent(event: Event, imageUri: Uri): Flow<Resource<Unit>> =
        eventRepository.addEvent(event, imageUri)

    override fun getConferenceCategory(): Flow<Resource<List<ConferenceCategory>>> =
        eventRepository.getConferenceCategory()

    override fun getCompetitionCategory(): Flow<Resource<List<CompetitionCategory>>> =
        eventRepository.getCompetitionCategory()

    override fun getMyEvents(ids: List<String>): Flow<Resource<List<Event>>> =
        eventRepository.getMyEvents(ids)

    override fun getEventById(id: String): Flow<Resource<Event>> =
        eventRepository.getEventById(id)

    override fun updateEvent(event: Event): Flow<Resource<Unit>> =
        eventRepository.updateEvent(event)

    override fun deleteEvent(id: String): Flow<Resource<Unit>> =
        eventRepository.deleteEvent(id)

    override fun refreshUser(): Flow<Resource<Unit>> =
        eventRepository.refreshUser()
}