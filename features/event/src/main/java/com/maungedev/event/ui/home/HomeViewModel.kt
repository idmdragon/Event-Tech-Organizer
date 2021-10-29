package com.maungedev.event.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.maungedev.domain.usecase.EventUseCase
import com.maungedev.domain.utils.Resource

class HomeViewModel(private val useCase: EventUseCase) : ViewModel() {

    fun getAllMyEvent(ids: List<String>) = useCase.getMyEvents(ids).asLiveData()

    fun getCurrentUser() = useCase.getCurrentUser().asLiveData()

    fun deleteEvent(id: String) = useCase.deleteEvent(id).asLiveData()

    fun refreshUser() : LiveData<Resource<Unit>> = useCase.refreshUser().asLiveData()

}