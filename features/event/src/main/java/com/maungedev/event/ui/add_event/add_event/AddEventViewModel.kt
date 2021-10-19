package com.maungedev.event.ui.add_event.add_event

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.maungedev.domain.model.Event
import com.maungedev.domain.model.User
import com.maungedev.domain.usecase.EventUseCase
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class AddEventViewModel(private val useCase: EventUseCase) : ViewModel() {

    private val imageUri = MutableLiveData<Uri>()

    fun addEvent(event: Event, imageUri: Uri) =
        useCase.addEvent(event, imageUri).asLiveData()

    fun setImageUri(uri: Uri) {
        this.imageUri.value = uri
    }

//    fun getCurrentUser() = useCase.getCurrentUser().asLiveData()

    fun getImageUri(): LiveData<Uri> = imageUri

    fun getConferenceCategory() = useCase.getConferenceCategory().asLiveData()

    fun getCompetitionCategory() = useCase.getCompetitionCategory().asLiveData()


}