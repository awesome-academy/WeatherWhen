package com.thailam.weatherwhen.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thailam.weatherwhen.data.model.DailyForecast
import com.thailam.weatherwhen.data.model.DailyForecast.Companion.TBL_FORECAST_NAME
import io.reactivex.Single

@Dao
abstract class DailyForecastDao {
    @Query(value = "SELECT * FROM $TBL_FORECAST_NAME")
    abstract fun getDailyForecasts(): Single<List<DailyForecast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addDailyForecasts(dailyForecasts: List<DailyForecast>)

    @Query(value = "SELECT * FROM $TBL_FORECAST_NAME LIMIT 1")
    abstract fun getLastUpdatedForecast(): DailyForecast

    @Query(value = "DELETE FROM $TBL_FORECAST_NAME")
    abstract fun deleteAllForecasts()
}
