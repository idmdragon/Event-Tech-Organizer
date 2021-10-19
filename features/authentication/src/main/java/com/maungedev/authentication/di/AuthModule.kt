package com.maungedev.authentication.di

import com.maungedev.authentication.ui.login.LoginViewModel
import com.maungedev.authentication.ui.register.RegisterViewModel
import com.maungedev.domain.usecase.AuthInteractor
import com.maungedev.domain.usecase.AuthUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    factory<AuthUseCase> {
        AuthInteractor(get())
    }
    viewModel {
        RegisterViewModel(get())
    }
    viewModel {
        LoginViewModel(get())
    }
}


