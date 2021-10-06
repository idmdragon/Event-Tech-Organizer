package com.maungedev.data.source.remote

import com.maungedev.data.source.remote.service.AuthService
import com.maungedev.domain.model.User

class RemoteDataSource(
    private val authService: AuthService
) {
    fun signUp(email:String, password:String, user: User) =
        authService.signUp(email,password,user)

    fun signIn(email:String, password:String) =
        authService.signIn(email,password)
}