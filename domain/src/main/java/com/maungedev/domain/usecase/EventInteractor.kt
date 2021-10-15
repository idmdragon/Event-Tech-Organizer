package com.maungedev.domain.usecase

import com.maungedev.domain.model.Event
import com.maungedev.domain.repository.EventITRepository
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import android.net.Uri
import com.maungedev.domain.model.CompetitionCategory
import com.maungedev.domain.model.ConferenceCategory
import com.maungedev.domain.model.User

class EventInteractor(private val eventITRepository: EventITRepository): EventUseCase {
    override fun getCurrentUser(): Flow<Resource<User>> =
        eventITRepository.getCurrentUser()

    override fun addEvent(event: Event, imageUri: Uri): Flow<Resource<Unit>> =
        eventITRepository.addEvent(event, imageUri)

    override fun getConferenceCategory(): Flow<Resource<List<ConferenceCategory>>> =
        eventITRepository.getConferenceCategory()

    override fun getCompetitionCategory(): Flow<Resource<List<CompetitionCategory>>> =
        eventITRepository.getCompetitionCategory()
}