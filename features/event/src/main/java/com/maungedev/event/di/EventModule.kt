package com.maungedev.event.di

import com.maungedev.domain.usecase.EventInteractor
import com.maungedev.domain.usecase.EventUseCase
import com.maungedev.event.ui.add.add_event.AddEventViewModel
import com.maungedev.event.ui.edit.EditEventViewModel
import com.maungedev.event.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventModule = module {
    factory<EventUseCase> {
        EventInteractor(get())
    }
    viewModel {
        AddEventViewModel(get())
    }
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        EditEventViewModel(get())
    }
}

