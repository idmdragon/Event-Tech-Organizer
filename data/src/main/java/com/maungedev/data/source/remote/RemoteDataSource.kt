package com.maungedev.data.source.remote

import android.net.Uri
import com.maungedev.data.source.remote.response.CompetitionCategoryResponse
import com.maungedev.data.source.remote.response.ConferenceCategoryResponse
import com.maungedev.data.source.remote.response.UserResponse
import com.maungedev.data.source.remote.service.AuthService
import com.maungedev.data.source.remote.service.EventService
import com.maungedev.data.source.remote.service.UserService
import com.maungedev.domain.model.Event
import com.maungedev.domain.model.User
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(
    private val authService: AuthService,
    private val eventService: EventService,
    private val userService: UserService

) {
    fun signUp(email:String, password:String, user: User) =
        authService.signUp(email,password,user)

    fun signIn(email:String, password:String) =
        authService.signIn(email,password)

    fun getCurrentUser(): Flow<FirebaseResponse<UserResponse>> =
        userService.getCurrentUser()

    fun insertEvent(event: Event, imageUri: Uri) =
        eventService.insertEvent(event,imageUri)

    fun getAllConferenceCategory():Flow<FirebaseResponse<List<ConferenceCategoryResponse>>> =
        eventService.getAllConferenceCategory()

    fun getAllCompetitionCategory():Flow<FirebaseResponse<List<CompetitionCategoryResponse>>> =
        eventService.getAllCompetitionCategory()
}