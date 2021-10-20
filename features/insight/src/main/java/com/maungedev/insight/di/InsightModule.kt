package com.maungedev.insight.di

import com.maungedev.domain.usecase.InsightInteractor
import com.maungedev.domain.usecase.InsightUseCase
import com.maungedev.insight.ui.InsightViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val insightModule = module {
    factory<InsightUseCase> {
        InsightInteractor(get())
    }
    viewModel {
        InsightViewModel(get())
    }
}

