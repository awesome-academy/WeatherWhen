package com.thailam.weatherwhen.di

import com.thailam.weatherwhen.data.ForecastDataSource
import com.thailam.weatherwhen.data.local.AppDatabase
import com.thailam.weatherwhen.data.local.source.ForecastLocalDataSource
import com.thailam.weatherwhen.data.remote.AccuWeatherRetrofitInstance
import com.thailam.weatherwhen.data.remote.source.ForecastRemoteDataSource
import com.thailam.weatherwhen.data.repository.ForecastRepository
import com.thailam.weatherwhen.data.repository.ForecastRepositoryImpl
import com.thailam.weatherwhen.viewmodel.ForecastViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val forecastModule = module(override = true) {
    single { AppDatabase.getInstance(androidContext()) }
    single { AccuWeatherRetrofitInstance.getInstance() }
    single<ForecastDataSource.Remote> { ForecastRemoteDataSource(get()) }
    single<ForecastDataSource.Local> { ForecastLocalDataSource(get()) }
    factory { get<AppDatabase>().dailyForecastDao() }
    single<ForecastRepository> { ForecastRepositoryImpl(get(), get()) }
}

val viewModelModule = module(override = true) {
    viewModel { ForecastViewModel(get()) }
}
