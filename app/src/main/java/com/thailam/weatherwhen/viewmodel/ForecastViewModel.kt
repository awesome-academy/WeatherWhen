package com.thailam.weatherwhen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thailam.weatherwhen.data.model.DailyForecast
import com.thailam.weatherwhen.data.model.Response
import com.thailam.weatherwhen.data.repository.ForecastRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ForecastViewModel(
    val forecastRepository: ForecastRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _dailyForecastsLiveData: MutableLiveData<Response<List<DailyForecast>>> by lazy {
        MutableLiveData<Response<List<DailyForecast>>>()
    }

    val dailyForecastsLiveData: LiveData<Response<List<DailyForecast>>>
        get() = _dailyForecastsLiveData

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun fetchDailyForecasts(geoPosition: Pair<String, String>) {
        compositeDisposable.add(
            forecastRepository.fetchDailyForecasts(geoPosition)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    _dailyForecastsLiveData.postValue(Response.loading())
                }
                .doOnSuccess {
                    forecastRepository.addDailyForecasts(it)
                }
                .subscribe({
                    _dailyForecastsLiveData.postValue(Response.success(it))
                }, {
                    _dailyForecastsLiveData.postValue(Response.error(it.message))
                })
        )
    }
}
