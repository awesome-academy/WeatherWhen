package com.thailam.weatherwhen.data

import com.thailam.weatherwhen.data.model.DailyForecast
import com.thailam.weatherwhen.data.model.LocationResponse
import com.thailam.weatherwhen.data.model.WeatherResponse
import io.reactivex.Single

interface ForecastDataSource {
    interface Local {
        fun addDailyForecasts(dailyForecasts: List<DailyForecast>)
        fun getDailyForecasts() : Single<List<DailyForecast>>
    }

    interface Remote {
        fun getLocationKey(geoposition: String): Single<LocationResponse>
        fun get5DaysForecast(locationKey: String): Single<WeatherResponse>
    }
}
