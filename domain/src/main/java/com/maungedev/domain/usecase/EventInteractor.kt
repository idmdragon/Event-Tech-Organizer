package com.maungedev.domain.usecase

import com.maungedev.domain.model.Event
import com.maungedev.domain.repository.EventITRepository
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import android.net.Uri

class EventInteractor(private val eventITRepository: EventITRepository): EventUseCase {
    override fun addEvent(event: Event, imageUri: Uri): Flow<Resource<Unit>> =
        eventITRepository.addEvent(event, imageUri)

}