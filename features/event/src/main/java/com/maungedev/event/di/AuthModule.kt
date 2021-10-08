package com.maungedev.event.di

import com.maungedev.domain.usecase.EventInteractor
import com.maungedev.domain.usecase.EventUseCase
import com.maungedev.event.ui.add_event.add_event.AddEventViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventModule = module {
    factory<EventUseCase> {
        EventInteractor(get())
    }
    viewModel {
        AddEventViewModel(get())

    }


}

