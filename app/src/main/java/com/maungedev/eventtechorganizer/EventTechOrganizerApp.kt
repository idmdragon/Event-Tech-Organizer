package com.maungedev.eventtechorganizer

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.maungedev.data.di.dataSourceModule
import com.maungedev.data.di.databaseModule
import com.maungedev.data.di.repositoryModule
import com.maungedev.data.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class EventTechOrganizerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@EventTechOrganizerApp)
            loadKoinModules(
                listOf(
                    databaseModule,
                    serviceModule,
                    dataSourceModule,
                    repositoryModule
                )
            )
        }
    }
}