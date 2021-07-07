package com.thailam.weatherwhen.data

import com.thailam.weatherwhen.data.model.Schedule
import io.reactivex.Single

interface ScheduleDataSource {
    interface Local {
        fun getAllSchedules(): Single<List<Schedule>>
        fun addSchedule(schedule: Schedule): Long
        fun deleteScheduleById(schedule: Schedule): Int
    }
}
