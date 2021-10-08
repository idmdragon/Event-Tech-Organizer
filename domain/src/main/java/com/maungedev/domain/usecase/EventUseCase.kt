package com.maungedev.domain.usecase

import com.maungedev.domain.model.Event
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import android.net.Uri

interface EventUseCase {
    fun addEvent(event: Event, imageUri: Uri):Flow<Resource<Unit>>
}