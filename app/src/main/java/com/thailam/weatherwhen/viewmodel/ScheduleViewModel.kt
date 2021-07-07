package com.thailam.weatherwhen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thailam.weatherwhen.data.model.Schedule
import com.thailam.weatherwhen.data.repository.ScheduleRepository

class ScheduleViewModel(val scheduleRepository: ScheduleRepository) : ViewModel() {
    private val _schedulesLiveData: MutableLiveData<List<Schedule>> by lazy {
        MutableLiveData<List<Schedule>>()
    }

    val schedulesLiveData: LiveData<List<Schedule>>
        get() = schedulesLiveData
}
