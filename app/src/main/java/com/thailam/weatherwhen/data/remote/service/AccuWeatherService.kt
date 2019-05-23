package com.thailam.weatherwhen.data.remote.service

import com.thailam.weatherwhen.data.model.LocationResponse
import com.thailam.weatherwhen.data.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// End point for location search
const val LOCATION_ENDPOINT = "locations/v1/cities/geoposition/search"
// End point for forecasts in a specific location
const val PATH_LOCATION_KEY = "locationKey"
const val FIVE_DAYS_FORECAST_ENDPOINT = "forecasts/v1/daily/5day/{$PATH_LOCATION_KEY}" //353412
/**
 * Query param to determine response unit in metric or imperial
 *      true->metric else false-> imperial
 * METRIC_QUERY_PARAM: to get response in metric unit
 * METRIC_QUERY_DEFAULT_VALUE: default value for metric : true
 */
private const val METRIC_QUERY_PARAM = "metric"
private const val METRIC_QUERY_DEFAULT_VALUE = "true"
/**
 * Query param to determine response is in detail or not
 *      true->response in detail else false -> not in detail
 * DETAIL_QUERY_PARAM: name for details query
 * DETAIL_QUERY__DEFAULT_VALUE: default value for details : true
 */
private const val DETAIL_QUERY_PARAM = "details"
private const val DETAIL_QUERY__DEFAULT_VALUE = "true"
/**
 * Query param to determine response location is top level or not
 *      ex: toplevel -> Hanoi, !topLevel -> BaDing, Hanoi
 * TOP_LEVEL_QUERY_PARAM: name for top level query
 * TOP_LEVEL_QUERY_DEFAULT_VALUE: default value for top level query : true
 */
private const val TOP_LEVEL_QUERY_PARAM = "toplevel"
private const val TOP_LEVEL_QUERY_DEFAULT_VALUE = "true"

interface AccuWeatherService {
    @GET(LOCATION_ENDPOINT)
    fun getLocationKey(
        @Query("q") geoposition: String,
        @Query(TOP_LEVEL_QUERY_PARAM) topLevel: String = TOP_LEVEL_QUERY_DEFAULT_VALUE
    ): Single<LocationResponse>

    @GET(FIVE_DAYS_FORECAST_ENDPOINT)
    fun get5DaysWeatherForecasts(
        @Path(PATH_LOCATION_KEY) locationKey: String,
        @Query(METRIC_QUERY_PARAM) metric: String = METRIC_QUERY_DEFAULT_VALUE,
        @Query(DETAIL_QUERY_PARAM) details: String = DETAIL_QUERY__DEFAULT_VALUE
    ): Single<WeatherResponse>
}
