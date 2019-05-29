package com.thailam.weatherwhen.data

import com.thailam.weatherwhen.data.model.DailyForecast
import com.thailam.weatherwhen.data.model.LocationResponse
import com.thailam.weatherwhen.data.model.WeatherResponse
import io.reactivex.Single

interface ForecastDataSource {
    interface Local {
        fun addDailyForecasts(dailyForecasts: List<DailyForecast>)
        fun getDailyForecastsLocal(): Single<List<DailyForecast>>
        fun getLastUpdatedForecast(): DailyForecast
        fun deleteAllForecasts()
    }

    interface Remote {
        fun getLocationKey(geoposition: String): Single<LocationResponse>
        fun getDailyForecastsRemote(locationKey: String): Single<WeatherResponse>
    }
}
