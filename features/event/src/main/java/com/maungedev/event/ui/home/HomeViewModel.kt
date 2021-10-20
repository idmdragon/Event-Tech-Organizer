package com.maungedev.event.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.maungedev.domain.usecase.EventUseCase

class HomeViewModel(private val useCase: EventUseCase) : ViewModel() {

    fun getAllMyEvent(ids: List<String>) = useCase.getMyEvents(ids).asLiveData()


}