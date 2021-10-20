package com.maungedev.insight.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.maungedev.domain.usecase.InsightUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InsightViewModel(private val useCase: InsightUseCase) : ViewModel() {

    private val _totalEvent = MutableLiveData<Int>()
    val totalEvent: LiveData<Int> = _totalEvent

    private val _totalRegistrationClick = MutableLiveData<Int>()
    val totalRegistrationClick : LiveData<Int> = _totalRegistrationClick

    private val _totalView = MutableLiveData<Int>()
    val totalView : LiveData<Int> = _totalView

    fun getAllMyEvent(ids: List<String>) = useCase.getMyEvents(ids).asLiveData()

    fun getTotalEvent() = CoroutineScope(Dispatchers.IO).launch {
       _totalEvent.postValue(useCase.getTotalEvent())
    }

    fun getTotalRegistrationClick() = CoroutineScope(Dispatchers.IO).launch {
        _totalRegistrationClick.postValue(useCase.getTotalRegistrationClick())
    }

    fun getTotalView()= CoroutineScope(Dispatchers.IO).launch {
        _totalView.postValue(useCase.getTotalView())
    }

}