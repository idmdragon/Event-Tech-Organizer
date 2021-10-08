package com.maungedev.data.source.remote

import android.net.Uri
import com.maungedev.data.source.remote.service.AuthService
import com.maungedev.data.source.remote.service.EventService
import com.maungedev.domain.model.Event
import com.maungedev.domain.model.User

class RemoteDataSource(
    private val authService: AuthService,
    private val eventService: EventService

) {
    fun signUp(email:String, password:String, user: User) =
        authService.signUp(email,password,user)

    fun signIn(email:String, password:String) =
        authService.signIn(email,password)

    fun insertEvent(event: Event, imageUri: Uri) =
        eventService.insertEvent(event,imageUri)
}