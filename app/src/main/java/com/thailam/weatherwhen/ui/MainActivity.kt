package com.thailam.weatherwhen.ui

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.thailam.weatherwhen.R
import com.thailam.weatherwhen.ui.base.BaseActivity
import com.thailam.weatherwhen.viewmodel.ForecastViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val forecastViewModel: ForecastViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        checkPermissions()
    }

    override fun doOnFineLocationGranted() {
        getLastLocation()
    }

    private fun initViewModel() {
        forecastViewModel.dailyForecastsLiveData.observe(this, Observer {
            // TODO: update UI in load ata to UI task
        })
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
}
