package com.thailam.weatherwhen.data.remote

import com.thailam.weatherwhen.BuildConfig
import com.thailam.weatherwhen.data.remote.service.AccuWeatherService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AccuWeatherRetrofitInstance {
    /**
     * Base endpoint to access accuweather api
     */
    private const val BASE_URL = "http://dataservice.accuweather.com/"
    /**
     * API_Key: key for accuweather api
     * API_KEY_QUERY_PARAM : query parameter in request
     */
    private const val API_KEY_QUERY_PARAM = "apikey"
    private const val API_KEY_QUERY_VALUE = BuildConfig.API_KEY

    private val requestInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url().newBuilder()
            .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY_QUERY_VALUE)
            .build()
        val request = chain.request().newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .build()

    fun getInstance(): AccuWeatherService =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(AccuWeatherService::class.java)
}
