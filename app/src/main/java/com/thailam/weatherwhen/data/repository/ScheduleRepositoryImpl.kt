package com.thailam.weatherwhen.data.repository

import com.thailam.weatherwhen.data.ScheduleDataSource
import com.thailam.weatherwhen.data.model.Schedule
import io.reactivex.Single

class ScheduleRepositoryImpl(
    val scheduleLocalDataSource: ScheduleDataSource.Local
) : ScheduleRepository {
    override fun getAllSchedules(): Single<List<Schedule>> =
        scheduleLocalDataSource.getAllSchedules()

    override fun addSchedule(schedule: Schedule): Long =
        scheduleLocalDataSource.addSchedule(schedule)

    override fun deleteScheduleById(schedule: Schedule): Int =
        scheduleLocalDataSource.deleteScheduleById(schedule)
}

interface ScheduleRepository : ScheduleDataSource.Local {
}
