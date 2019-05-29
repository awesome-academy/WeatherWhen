package com.thailam.weatherwhen.ui.base

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.thailam.weatherwhen.R

/**
 * Base class for activities, contains
 *  1. Permissions
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if ((grantResults.isNotEmpty()) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    doOnFineLocationGranted()
                } else {
                    onPermissionDenied()
                }
                return
            }
        }
    }

    fun checkPermissions() {
        if (isPermissionGranted(PERMISSION_FINE_LOCATION)) { // if permission is granted
            doOnFineLocationGranted()
        } else { // ask for permission if is not granted
            promptRequestPermissions()
        }
    }

    open fun doOnFineLocationGranted() {
    }

    private fun onPermissionDenied() {
        val msg = resources.getString(R.string.on_location_permission_denided)
        val undo = resources.getString(R.string.undo_string)
        Snackbar.make(window.decorView, msg, Snackbar.LENGTH_LONG)
            .setActionTextColor(Color.YELLOW)
            .setAction(undo) {
                promptRequestPermissions()
            }
            .show()
    }

    private fun promptRequestPermissions() =
        ActivityCompat.requestPermissions(this, PERMISSION_ARRAY, PERMISSION_CODE)

    private fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    companion object {
        /**
         * PERMISSION_FINE_LOCATION_CODE: permission code for Manifest.ACCESS_FINE_LOCATION
         * PERMISSION FINE LOCATION: String value of Manifest.permission.ACCESS_FINE_LOCATION
         */
        private const val PERMISSION_CODE = 1
        private const val PERMISSION_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION
        private val PERMISSION_ARRAY = arrayOf(PERMISSION_FINE_LOCATION)
    }
}
