package com.thailam.weatherwhen.data.local.source

import com.thailam.weatherwhen.data.ForecastDataSource
import com.thailam.weatherwhen.data.local.dao.DailyForecastDao
import com.thailam.weatherwhen.data.model.DailyForecast
import io.reactivex.Single

class ForecastLocalDataSource(
    val forecastDao: DailyForecastDao
) : ForecastDataSource.Local {
    override fun getLastUpdatedForecast(): DailyForecast =
        forecastDao.getLastUpdatedForecast()

    override fun getDailyForecastsLocal(): Single<List<DailyForecast>> =
        forecastDao.getDailyForecasts()

    override fun addDailyForecasts(dailyForecasts: List<DailyForecast>) =
        forecastDao.addDailyForecasts(dailyForecasts)

    override fun deleteAllForecasts() =
        forecastDao.deleteAllForecasts()
}
