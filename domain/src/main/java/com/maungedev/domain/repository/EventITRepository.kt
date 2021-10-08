package com.maungedev.domain.repository

import android.net.Uri
import com.maungedev.domain.model.Event
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface EventITRepository {
    fun addEvent(event: Event, imageUri: Uri): Flow<Resource<Unit>>
}