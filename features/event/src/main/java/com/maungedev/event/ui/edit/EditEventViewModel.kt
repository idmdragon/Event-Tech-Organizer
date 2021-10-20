package com.maungedev.event.ui.edit

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.maungedev.domain.model.Event
import com.maungedev.domain.model.User
import com.maungedev.domain.usecase.EventUseCase

class EditEventViewModel(private val useCase: EventUseCase) : ViewModel() {

    private val imageUri = MutableLiveData<Uri>()

    fun setImageUri(uri: Uri) {
        this.imageUri.value = uri
    }

    fun getImageUri(): LiveData<Uri> = imageUri

    fun updateEvent(event: Event) =
        useCase.updateEvent(event).asLiveData()

    fun getEventById(id: String) = useCase.getEventById(id).asLiveData()

    fun getConferenceCategory() = useCase.getConferenceCategory().asLiveData()

    fun getCompetitionCategory() = useCase.getCompetitionCategory().asLiveData()
}