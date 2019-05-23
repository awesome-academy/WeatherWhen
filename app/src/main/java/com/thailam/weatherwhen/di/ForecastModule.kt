package com.thailam.weatherwhen.di

import com.thailam.weatherwhen.data.ForecastDataSource
import com.thailam.weatherwhen.data.local.AppDatabase
import com.thailam.weatherwhen.data.local.ForecastLocalDataSource
import com.thailam.weatherwhen.data.remote.AccuWeatherRetrofitInstance
import com.thailam.weatherwhen.data.remote.ForecastRemoteDataSource
import com.thailam.weatherwhen.data.repository.ForecastRepository
import com.thailam.weatherwhen.data.repository.ForecastRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val forecastModule = module(override = true) {
    single { AppDatabase.getInstance(androidContext()) }
    single { AccuWeatherRetrofitInstance.getInstance() }
    single<ForecastDataSource.Remote> { ForecastRemoteDataSource(get()) }
    single<ForecastDataSource.Local> { ForecastLocalDataSource(get()) }
    factory { get<AppDatabase>().dailyForecastDao() }
    single<ForecastRepository> { ForecastRepositoryImpl(get(), get()) }
}
