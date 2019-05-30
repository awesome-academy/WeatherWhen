package com.thailam.weatherwhen.data.model

data class DisplayedForecast(
    val id : Int,
    val epochDate : Long,
    val temperatureValue : Double,
    val temperatureUnit : String,
    val currentCondition: CurrentCondition
)
