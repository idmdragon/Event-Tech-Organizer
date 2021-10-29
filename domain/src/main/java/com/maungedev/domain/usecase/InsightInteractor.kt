package com.maungedev.domain.usecase

import com.maungedev.domain.model.Event
import com.maungedev.domain.model.User
import com.maungedev.domain.repository.EventRepository
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class InsightInteractor (private val eventRepository: EventRepository):InsightUseCase{

    override fun getCurrentUser(): Flow<Resource<User>> =
        eventRepository.getCurrentUser()

    override fun getMyEvents(ids: List<String>): Flow<Resource<List<Event>>> =
        eventRepository.getMyEvents(ids)

    override fun getTotalEvent(): Int =
        eventRepository.getTotalEvent()

    override fun getTotalRegistrationClick(): Int =
        eventRepository.getTotalRegistrationClick()

    override fun getTotalView(): Int =
        eventRepository.getTotalView()

    override fun refreshAllEvent(ids: List<String>): Flow<Resource<Unit>> =
        eventRepository.refreshAllEvent(ids)
}