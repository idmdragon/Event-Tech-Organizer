package com.maungedev.profile.di

import com.maungedev.domain.usecase.ProfileInteractor
import com.maungedev.domain.usecase.ProfileUseCase
import com.maungedev.profile.ui.ProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    factory<ProfileUseCase> {
        ProfileInteractor(get())
    }
    viewModel {
        ProfileViewModel(get())
    }
}

