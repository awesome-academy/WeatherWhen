package com.thailam.weatherwhen.data.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class CurrentCondition(
    @SerializedName(API_ICON) val icon: Int,
    @SerializedName(API_PHRASE) val phrase: String,
    @SerializedName(API_RAIN_PROP) val rainProp: Int,
    @Embedded(prefix = TBL_CONDITION_PREFIX_RAIN)
    @SerializedName(API_RAIN)
    val rain: Rain,
    @Embedded(prefix = TBL_CONDITION_PREFIX_WIND)
    @SerializedName(API_WIND)
    val wind: Wind
) {
    companion object {
        const val API_ICON = "Icon"
        const val API_PHRASE = "ShortPhrase"
        const val API_RAIN_PROP = "RainProbability"
        const val API_RAIN = "Rain"
        const val API_WIND = "Wind"
        // db prefixes
        const val TBL_CONDITION_PREFIX_RAIN = "rain_"
        const val TBL_CONDITION_PREFIX_WIND = "wind_"
    }
}


data class Rain(
    @SerializedName(API_VALUE) val value: Double,
    @SerializedName(API_UNIT) val unit: String
) {
    companion object {
        const val API_VALUE = "Value"
        const val API_UNIT = "Unit"
    }
}

data class Wind(
    @Embedded(prefix = TBL_CONDITION_PREFIX_SPEED) @SerializedName(API_SPEED)
    val speed: Speed,
    @Embedded(prefix = TBL_CONDITION_PREFIX_DIRECTION) @SerializedName(API_DIRECTION)
    val direction: Direction
) {
    companion object {
        const val API_SPEED = "Speed"
        const val API_DIRECTION = "Direction"
        // db prefixes
        const val TBL_CONDITION_PREFIX_SPEED = "speed_"
        const val TBL_CONDITION_PREFIX_DIRECTION = "direction_"
    }
}

data class Speed(
    @SerializedName(API_VALUE) val value: Double,
    @SerializedName(API_UNIT) val unit: String
) {
    companion object {
        const val API_VALUE = "Value"
        const val API_UNIT = "Unit"
    }
}

data class Direction(
    @SerializedName(API_DEGREES) val degrees: Double,
    @SerializedName(API_ENGLISH) val englishDir: String
) {
    companion object {
        const val API_DEGREES = "Degrees"
        const val API_ENGLISH = "English"
    }
}
