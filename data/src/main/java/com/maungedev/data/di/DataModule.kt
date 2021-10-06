package com.maungedev.data.di

import androidx.room.Room
import com.maungedev.data.repository.AuthRepositoryImpl
import com.maungedev.data.source.local.EventTechDatabase
import com.maungedev.data.source.local.LocalDataSource
import com.maungedev.data.source.remote.RemoteDataSource
import com.maungedev.data.source.remote.service.AuthService
import com.maungedev.domain.repository.AuthRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            EventTechDatabase::class.java,
            "EventTechOrganizer.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
    factory {
        get<EventTechDatabase>().userDao()
    }
}

val serviceModule = module {
    factory {
        AuthService()
    }
}

val dataSourceModule = module {
    single {
        LocalDataSource(get())
    }
    single {
        RemoteDataSource(get())
    }
}

val repositoryModule = module {
    single<AuthRepository>{
        AuthRepositoryImpl(get(),get())
    }
}