package com.thailam.weatherwhen.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.thailam.weatherwhen.data.model.DailyForecast.Companion.TBL_FORECAST_NAME

@Entity(tableName = TBL_FORECAST_NAME)
data class DailyForecast(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TBL_FORECAST_ID)
    val id: Int,
    @SerializedName(API_DATE) @ColumnInfo(name = TBL_FORECAST_DATE)
    val date: String,
    @SerializedName(API_EPOCH_DATE) @ColumnInfo(name = TBL_FORECAST_EPOCH)
    val epochDate: Long,
    @Embedded(prefix = TBL_FORECAST_TEMPERATURE)
    @SerializedName(API_TEMPERATURE)
    val temperature: Temperature,
    @Embedded(prefix = TBL_FORECAST_PREFIX_DAY)
    @SerializedName(API_DAY)
    val day: CurrentCondition,
    @Embedded(prefix = TBL_FORECAST_PREFIX_NIGHT)
    @SerializedName(API_NIGHT)
    val night: CurrentCondition
) {
    companion object {
        // API responses serialized name
        const val API_DATE = "Date"
        const val API_EPOCH_DATE = "EpochDate"
        const val API_TEMPERATURE = "Temperature"
        const val API_DAY = "Day"
        const val API_NIGHT = "Night"
        // Local database entries
        const val TBL_FORECAST_NAME = "daily_forecasts"
        const val TBL_FORECAST_ID = "id"
        const val TBL_FORECAST_DATE = "date"
        const val TBL_FORECAST_EPOCH = "epoch_date"
        const val TBL_FORECAST_TEMPERATURE = "temperature"
        const val TBL_FORECAST_PREFIX_DAY = "day_"
        const val TBL_FORECAST_PREFIX_NIGHT = "night_"

    }
}

data class Temperature(
    @SerializedName(API_MAX_TEMP) @Embedded(prefix = TBL_MAX_TEMP_PREFIX)
    val maxTemp: TemperatureValue,
    @SerializedName(API_MIN_TEMP) @Embedded(prefix = TBL_MIN_TEMP_PREFIX)
    val minTemp: TemperatureValue
) {
    companion object {
        const val API_MAX_TEMP = "Maximum"
        const val API_MIN_TEMP = "Minimum"
        // local db entries
        const val TBL_MAX_TEMP_PREFIX = "max_"
        const val TBL_MIN_TEMP_PREFIX = "min_"
    }
}

data class TemperatureValue(
    @SerializedName(API_VALUE)
    val value: Double,
    @SerializedName(API_UNIT)
    val unit: String
) {
    companion object {
        const val API_VALUE = "Value"
        const val API_UNIT = "Unit"
    }
}
