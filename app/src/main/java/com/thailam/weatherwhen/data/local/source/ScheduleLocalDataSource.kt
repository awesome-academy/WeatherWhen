package com.thailam.weatherwhen.data.local.source

import com.thailam.weatherwhen.data.ScheduleDataSource
import com.thailam.weatherwhen.data.local.dao.ScheduleDao
import com.thailam.weatherwhen.data.model.Schedule
import io.reactivex.Single

class ScheduleLocalDataSource(
    val scheduleDao: ScheduleDao
) : ScheduleDataSource.Local {
    override fun getAllSchedules(): Single<List<Schedule>> =
        scheduleDao.getAllSchedules()

    override fun addSchedule(schedule: Schedule): Long =
        scheduleDao.addSchedule(schedule)

    override fun deleteScheduleById(schedule: Schedule): Int =
        scheduleDao.deleteScheduleById(schedule)
}
