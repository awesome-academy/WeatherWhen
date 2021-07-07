package com.thailam.weatherwhen

import android.app.Application
import com.thailam.weatherwhen.di.forecastModule
import com.thailam.weatherwhen.di.scheduleModule
import com.thailam.weatherwhen.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(forecastModule, scheduleModule, viewModelModule))
        }
    }
}
