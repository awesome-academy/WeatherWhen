package com.thailam.weatherwhen.data.repository

import com.thailam.weatherwhen.data.ForecastDataSource
import com.thailam.weatherwhen.data.model.DailyForecast
import com.thailam.weatherwhen.data.model.LocationResponse
import com.thailam.weatherwhen.data.model.WeatherResponse
import io.reactivex.Single

class ForecastRepositoryImpl(
    val forecastRemoteDataSource: ForecastDataSource.Remote,
    val forecastLocalDataSource: ForecastDataSource.Local
) : ForecastRepository {
    override fun addDailyForecasts(dailyForecasts: List<DailyForecast>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDailyForecasts(): Single<List<DailyForecast>> =
        forecastLocalDataSource.getDailyForecasts()

    override fun getLocationKey(geoposition: String): Single<LocationResponse> =
        forecastRemoteDataSource.getLocationKey(geoposition)

    override fun get5DaysForecast(locationKey: String): Single<WeatherResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

interface ForecastRepository : ForecastDataSource.Remote, ForecastDataSource.Local
