package com.thailam.weatherwhen.data.local

import com.thailam.weatherwhen.data.ForecastDataSource
import com.thailam.weatherwhen.data.local.dao.DailyForecastDao
import com.thailam.weatherwhen.data.model.DailyForecast
import io.reactivex.Single

class ForecastLocalDataSource(
    val forecastDao: DailyForecastDao
) : ForecastDataSource.Local {
    override fun getDailyForecasts(): Single<List<DailyForecast>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addDailyForecasts(dailyForecasts: List<DailyForecast>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
