package com.thailam.weatherwhen.ui.schedule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thailam.weatherwhen.R
import kotlinx.android.synthetic.main.activity_schedule.*

class ScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbarSchedule)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        toolbarSchedule.setNavigationOnClickListener { finish() }
    }
}
