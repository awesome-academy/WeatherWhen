package com.thailam.weatherwhen.data.local.dao

import androidx.room.*
import com.thailam.weatherwhen.data.model.Schedule
import com.thailam.weatherwhen.data.model.Schedule.Companion.TBL_SCHEDULE_NAME
import io.reactivex.Single

@Dao
abstract class ScheduleDao {
    @Query(value = "SELECT * FROM $TBL_SCHEDULE_NAME")
    abstract fun getAllSchedules(): Single<List<Schedule>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addSchedule(schedule: Schedule): Long

    @Delete
    abstract fun deleteScheduleById(schedule: Schedule): Int
}
