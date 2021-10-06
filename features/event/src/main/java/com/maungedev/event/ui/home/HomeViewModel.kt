package com.maungedev.event.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maungedev.domain.model.Event
import com.maungedev.eventtechorganizer.dummy.DummyData

class HomeViewModel : ViewModel() {

    private val _listEvent = MutableLiveData<List<Event>>()

    fun getMyEvent() : LiveData<List<Event>> {
        _listEvent.value = DummyData.generateDummyEvent()
        return _listEvent
    }

}