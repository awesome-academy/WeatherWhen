package com.thailam.weatherwhen.ui.forecast

import android.annotation.SuppressLint
import android.content.Intent
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
import com.thailam.weatherwhen.data.model.*
import com.thailam.weatherwhen.ui.base.BaseActivity
import com.thailam.weatherwhen.ui.schedule.ScheduleActivity
import com.thailam.weatherwhen.utils.DateFormatUtils
import com.thailam.weatherwhen.utils.MyAnimationUtils
import com.thailam.weatherwhen.utils.appendUnit
import com.thailam.weatherwhen.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ForecastActivity : BaseActivity() {

    private val forecastViewModel: ForecastViewModel by viewModel()
    private var isCurrentNight: Boolean = false
    private var isAnimating = false
    private val forecastAdapter = ForecastAdapter()
    private var currentForecasts: List<DailyForecast>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        checkPermissions()
        initViews()
        initRecyclerView()
        loadBackgroundStateFromSavedInstance(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(BUNDLE_IS_NIGHT, isCurrentNight)
        super.onSaveInstanceState(outState)
    }

    override fun doOnFineLocationGranted() {
        getLastLocation()
    }

    private fun loadBackgroundStateFromSavedInstance(savedInstanceState: Bundle?) {
        val isCurrentNightLastState = savedInstanceState?.getBoolean(BUNDLE_IS_NIGHT)
        if (isCurrentNightLastState != null) {
            isCurrentNight = isCurrentNightLastState
            if (isCurrentNight) imageViewBgNight.visibility = View.VISIBLE
        }
    }

    private fun initViewModel() {
        forecastViewModel.dailyForecastsLiveData.observe(this, Observer {
            handleForecastsChange(it)
        })
    }

    private fun handleForecastsChange(response: Response<List<DailyForecast>>) {
        when (response.status) {
            Status.SUCCESS -> updateUI(response.data)
            Status.LOADING -> showProgressBar()
            Status.ERROR -> onGetForecastsError(response.message)
        }
    }

    private fun updateUI(forecasts: List<DailyForecast>?) {
        hideProgressBar()
        if (forecasts != null) {
            currentForecasts = forecasts
            bindCurrentWeather()
            forecastAdapter.submitList(convertForecastsToDisplayFormat(currentForecasts))
        } else {
            onGetForecastsError(resources.getString(R.string.error_no_forecasts))
        }
    }

    private fun convertForecastsToDisplayFormat(forecasts: List<DailyForecast>?): List<DisplayedForecast> {
        val displayForecasts = mutableListOf<DisplayedForecast>()
        forecasts?.forEach {
            displayForecasts.add(
                if (isCurrentNight) {
                    DisplayedForecast(
                        it.id, it.epochDate, it.temperature.minTemp.value,
                        it.temperature.minTemp.unit, it.night
                    )
                } else {
                    DisplayedForecast(
                        it.id, it.epochDate, it.temperature.maxTemp.value,
                        it.temperature.maxTemp.unit, it.day
                    )

                }
            )
        }
        return displayForecasts
    }

    private fun bindCurrentWeather() {
        if (currentForecasts != null) {
            val displayedWeather = currentForecasts!![0]
            val condition: CurrentCondition
            textLocationDate.text = DateFormatUtils.longToDefaultDateTime(this, displayedWeather.epochDate)
            if (!isCurrentNight) {
                condition = displayedWeather.day
                textViewTemp.text = displayedWeather.temperature.maxTemp.value.toInt().toString()
                textLocationDate.append(resources.getString(R.string.header_append_day))
            } else {
                condition = displayedWeather.night
                textViewTemp.text = displayedWeather.temperature.minTemp.value.toInt().toString()
                textLocationDate.append(resources.getString(R.string.header_append_night))
            }
            textViewPhrase.text = condition.phrase
            textViewRainPropValue.text =
                StringBuilder().appendUnit(condition.rainProp.toString(), resources.getString(R.string.percent))
            textViewWindSpeedValue.text = StringBuilder().appendUnit(
                condition.wind.speed.value.toString(),
                condition.wind.speed.unit
            )
        }
    }

    private fun onGetForecastsError(errorMsg: String?) {
        hideProgressBar()
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
    }

    private fun showProgressBar() {
        progressBarForecastScreen.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBarForecastScreen.visibility = View.GONE
    }

    private fun initViews() {
        textViewDayNight.setOnClickListener {
            toggleDayNightForecast()
        }
        fabOpenSchedule.setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
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
        isCurrentNight = !isCurrentNight
    }

    private fun toggleDayNightForecast() {
        if (isAnimating) return
        if (!isCurrentNight) {
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
                    updateUI(currentForecasts)
                    bindCurrentWeather()
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
                    updateUI(currentForecasts)
                    bindCurrentWeather()
                })
        }
        toggleBgState()
    }

    private fun toggleDayNightText(titleId: Int, colorId: Int) =
        with(textViewDayNight) {
            text = resources.getString(titleId)
            DrawableCompat.setTint(
                this.background,
                ContextCompat.getColor(this@ForecastActivity, colorId)
            )
        }

    private fun initRecyclerView() {
        recycler_forecast.apply {
            layoutManager = LinearLayoutManager(this@ForecastActivity)
            setHasFixedSize(true)
            adapter = forecastAdapter
        }
    }

    companion object {
        const val BUNDLE_IS_NIGHT = "BUNDLE_IS_NIGHT"
    }
}
