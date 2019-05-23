package com.thailam.weatherwhen.data.remote

import com.thailam.weatherwhen.data.ForecastDataSource
import com.thailam.weatherwhen.data.model.LocationResponse
import com.thailam.weatherwhen.data.model.WeatherResponse
import com.thailam.weatherwhen.data.remote.service.AccuWeatherService
import io.reactivex.Single

class ForecastRemoteDataSource(val accuWeatherService: AccuWeatherService) : ForecastDataSource.Remote {
    override fun getLocationKey(geoposition: String): Single<LocationResponse> =
        accuWeatherService.getLocationKey(geoposition)

    override fun get5DaysForecast(locationKey: String): Single<WeatherResponse> =
        accuWeatherService.get5DaysWeatherForecasts(locationKey)
}
