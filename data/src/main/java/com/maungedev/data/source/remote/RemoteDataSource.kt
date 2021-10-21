package com.maungedev.data.source.remote

import android.net.Uri
import com.maungedev.data.source.remote.response.*
import com.maungedev.data.source.remote.service.AuthService
import com.maungedev.data.source.remote.service.EventService
import com.maungedev.data.source.remote.service.UserService
import com.maungedev.domain.model.Event
import com.maungedev.domain.model.User
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(
    private val authService: AuthService,
    private val eventService: EventService,
    private val userService: UserService,
) {
    fun signUp(email: String, password: String, user: User) =
        authService.signUp(email, password, user)

    fun signIn(email: String, password: String) =
        authService.signIn(email, password)

    fun getCurrentUser(id: String): Flow<FirebaseResponse<UserResponse>> =
        userService.getUser(id)

    fun getCurrentUserId(): String =
        userService.getCurrentUserId()

    fun insertEvent(event: Event, imageUri: Uri):Flow<FirebaseResponse<EventResponse>> =
        eventService.insertEvent(event, imageUri)

    fun getAllConferenceCategory(): Flow<FirebaseResponse<List<ConferenceCategoryResponse>>> =
        eventService.getAllConferenceCategory()

    fun getAllCompetitionCategory(): Flow<FirebaseResponse<List<CompetitionCategoryResponse>>> =
        eventService.getAllCompetitionCategory()

    fun getMyEvents(ids: List<String>): Flow<FirebaseResponse<List<EventResponse>>> =
        eventService.getMyEvents(ids)

    fun getEventById(id: String): Flow<FirebaseResponse<EventResponse>> =
        eventService.getEventById(id)

    fun updateEvent(event: Event): Flow<FirebaseResponse<EventResponse>> =
        eventService.updateEvent(event)

    fun deleteEvent(id: String): Flow<FirebaseResponse<Unit>> =
        eventService.deleteEvent(id)

    fun updateUsername(username: String): Flow<FirebaseResponse<UserResponse>> =
        userService.updateUsername(username)


}