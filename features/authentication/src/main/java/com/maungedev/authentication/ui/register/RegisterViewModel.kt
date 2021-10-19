package com.maungedev.authentication.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.maungedev.domain.model.User
import com.maungedev.domain.usecase.AuthUseCase

class RegisterViewModel(private val authUseCase: AuthUseCase) : ViewModel() {
    fun signUp(email: String, password: String, user: User) =
        authUseCase.signUpUser(email,password,user).asLiveData()

}
