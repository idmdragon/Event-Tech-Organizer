package com.maungedev.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.maungedev.domain.model.User
import com.maungedev.domain.usecase.ProfileUseCase
import com.maungedev.domain.utils.Resource

class ProfileViewModel(private val useCase: ProfileUseCase) : ViewModel() {

    fun getCurrentUser() : LiveData<Resource<User>> =
        useCase.getCurrentUser().asLiveData()

    fun updateUsername(username: String) : LiveData<Resource<Unit>> =
        useCase.updateUsername(username).asLiveData()

    fun logout(): Unit =
        useCase.logout()
}