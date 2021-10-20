package com.maungedev.domain.usecase

import com.maungedev.domain.model.Event
import com.maungedev.domain.model.User
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface InsightUseCase {
    fun getCurrentUser(): Flow<Resource<User>>
    fun getMyEvents(ids: List<String>): Flow<Resource<List<Event>>>
    fun getTotalEvent():Int
    fun getTotalRegistrationClick(): Int
    fun getTotalView(): Int
}