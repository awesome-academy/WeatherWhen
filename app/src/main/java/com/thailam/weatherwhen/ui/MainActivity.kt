package com.thailam.weatherwhen.ui

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.thailam.weatherwhen.R
import com.thailam.weatherwhen.ui.base.BaseActivity
import com.thailam.weatherwhen.utils.MyAnimationUtils
import com.thailam.weatherwhen.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private val forecastViewModel: ForecastViewModel by viewModel()
    private var isBgDay: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        checkPermissions()
        initviews()
    }

    override fun doOnFineLocationGranted() {
        getLastLocation()
    }

    private fun initViewModel() {
        forecastViewModel.dailyForecastsLiveData.observe(this, Observer {
            // TODO: update UI in load ata to UI task
        })
    }

    private fun initviews() {
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
        if (!isBgDay) {
            MyAnimationUtils.createCustomCircularRevealAnimation(
                currentView = imageViewBgNight,
                revealView = imageViewBgNight,
                animationStart = { imageViewBgNight.visibility = View.VISIBLE },
                animationEnd = {
                    imageViewBgDay.visibility = View.GONE
                    textViewDayNight.apply {
                        text = resources.getString(R.string.day)
                        setBackgroundColor(resources.getColor(R.color.color_bg_day))
                    }
                })
            toggleBgState()
        } else {
            MyAnimationUtils.createCustomCircularRevealAnimation(
                currentView = imageViewBgNight,
                revealView = imageViewBgNight,
                reverse = true,
                animationStart = { imageViewBgDay.visibility = View.VISIBLE },
                animationEnd = {
                    imageViewBgNight.visibility = View.GONE
                    textViewDayNight.apply {
                        text = resources.getString(R.string.night)
                        setBackgroundColor(resources.getColor(R.color.color_bg_night))
                    }
                })
            toggleBgState()
        }
    }
}
