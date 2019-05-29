package com.thailam.weatherwhen.ui

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.thailam.weatherwhen.R
import com.thailam.weatherwhen.adapter.ForecastAdapter
import com.thailam.weatherwhen.data.model.DailyForecast
import com.thailam.weatherwhen.data.model.Response
import com.thailam.weatherwhen.data.model.Status
import com.thailam.weatherwhen.ui.base.BaseActivity
import com.thailam.weatherwhen.utils.MyAnimationUtils
import com.thailam.weatherwhen.utils.appendUnit
import com.thailam.weatherwhen.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private val forecastViewModel: ForecastViewModel by viewModel()
    private var isBgDay: Boolean = false
    private var isAnimating = false
    private val forecastAdapter = ForecastAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        checkPermissions()
        initViews()
        initRecyclerView()
    }

    override fun doOnFineLocationGranted() {
        getLastLocation()
    }

    private fun initViewModel() {
        forecastViewModel.dailyForecastsLiveData.observe(this, Observer {
            handleForecastsChange(it)
        })
    }

    private fun handleForecastsChange(response: Response<List<DailyForecast>>) {
        when (response.status) {
            Status.SUCCESS -> updateUI(response.data)
            Status.ERROR -> onGetForecastsError(response.message)
        }
    }

    private fun updateUI(forecasts: List<DailyForecast>?) {
        if (forecasts != null) {
            bindCurrentWeatherDay(forecasts[0])
            forecastAdapter.submitList(forecasts)
        } else {
            onGetForecastsError(resources.getString(R.string.error_no_forecasts))
        }
    }

    private fun bindCurrentWeatherDay(currentWeather: DailyForecast) {
        // TODO: implement in next task
    }

    private fun onGetForecastsError(errorMsg: String?) =
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()

    private fun initViews() {
        textViewDayNight.setOnClickListener {
            toggleDayNightForecast()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        val locationClient: FusedLocationProviderClient = getFusedLocationProviderClient(this)
        locationClient.lastLocation
            .addOnSuccessListener {
                onLocationChanged(location = it)
            }
            .addOnFailureListener {
                onLocationFailure(it)
            }
    }

    /**
     * updates the view model on location change (using latitude and longitude)
     */
    private fun onLocationChanged(location: Location?) {
        val geoposition = Pair(location?.latitude.toString(), location?.longitude.toString())
        forecastViewModel.fetchDailyForecasts(geoposition)
    }

    private fun onLocationFailure(e: Exception) =
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()

    /**
     *  Methods for animations handling
     */
    private fun toggleBgState() {
        isBgDay = !isBgDay
    }

    private fun toggleDayNightForecast() {
        if (isAnimating) return
        if (!isBgDay) {
            MyAnimationUtils.createCustomCircularRevealAnimation(
                currentView = imageViewBgNight,
                revealView = imageViewBgNight,
                animationStart = {
                    imageViewBgNight.visibility = View.VISIBLE
                    isAnimating = true
                },
                animationEnd = {
                    imageViewBgDay.visibility = View.INVISIBLE
                    toggleDayNightText(R.string.day, R.color.color_bg_day)
                    isAnimating = false
                })
        } else {
            MyAnimationUtils.createCustomCircularRevealAnimation(
                currentView = imageViewBgNight,
                revealView = imageViewBgNight,
                reverse = true,
                animationStart = {
                    imageViewBgDay.visibility = View.VISIBLE
                    isAnimating = true
                },
                animationEnd = {
                    imageViewBgNight.visibility = View.INVISIBLE
                    toggleDayNightText(R.string.night, R.color.color_bg_night)
                    isAnimating = false
                })
        }
        toggleBgState()
    }

    private fun toggleDayNightText(titleId: Int, colorId: Int) =
        with(textViewDayNight) {
            text = resources.getString(titleId)
            DrawableCompat.setTint(
                this.background,
                ContextCompat.getColor(this@MainActivity, colorId)
            )
        }

    private fun initRecyclerView() {
        recycler_forecast.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = forecastAdapter
        }
    }
}
