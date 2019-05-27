package com.thailam.weatherwhen.data.repository

import com.thailam.weatherwhen.data.ForecastDataSource
import com.thailam.weatherwhen.data.model.DailyForecast
import com.thailam.weatherwhen.data.model.LocationResponse
import com.thailam.weatherwhen.data.model.WeatherResponse
import com.thailam.weatherwhen.utils.CURRENT_DATE_Y_M_D
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ForecastRepositoryImpl(
    val forecastRemoteDataSource: ForecastDataSource.Remote,
    val forecastLocalDataSource: ForecastDataSource.Local
) : ForecastRepository {
    /**
     * main method to be used by view model to get daily forecasts
     */
    override fun fetchDailyForecasts(geoposition: Pair<String, String>): Single<List<DailyForecast>> {
        val shouldRefresh = runBlocking { checkIfShouldRefresh() }
        return if (shouldRefresh) {
            getLocationKey(geoposition.toLocationQueryFormat())
                .subscribeOn(Schedulers.io())
                .flatMap {
                    getDailyForecastsRemote(it.key)
                }
                .map {
                    it.dailyForecasts
                }
        } else { // no need to refresh/ no internet -> default to local database
            getDailyForecastsLocal()
        }
    }

    override fun addDailyForecasts(dailyForecasts: List<DailyForecast>) =
        forecastLocalDataSource.addDailyForecasts(dailyForecasts)

    override fun getDailyForecastsLocal(): Single<List<DailyForecast>> =
        forecastLocalDataSource.getDailyForecastsLocal()

    override fun getLocationKey(geoposition: String): Single<LocationResponse> =
        forecastRemoteDataSource.getLocationKey(geoposition)

    override fun getDailyForecastsRemote(locationKey: String): Single<WeatherResponse> =
        forecastRemoteDataSource.getDailyForecastsRemote(locationKey)

    override fun getLastUpdatedForecast(): DailyForecast =
        forecastLocalDataSource.getLastUpdatedForecast()

    private suspend fun checkIfShouldRefresh(): Boolean = coroutineScope {
        withContext(Dispatchers.IO) {
            val lastUpdatedDate = withContext(Dispatchers.IO) { getLastUpdatedForecast() }.date
            lastUpdatedDate.equals(CURRENT_DATE_Y_M_D, ignoreCase = true)
        }
    }

    // extension function to convert lat long pair to string compatible with api request
    private fun Pair<String, String>.toLocationQueryFormat(): String =
        StringBuilder().apply {
            append(this@toLocationQueryFormat.first)
            append(",")
            append(this@toLocationQueryFormat.second)
        }.toString()
}

interface ForecastRepository : ForecastDataSource.Remote, ForecastDataSource.Local {
    fun fetchDailyForecasts(geoposition: Pair<String, String>): Single<List<DailyForecast>>
}
