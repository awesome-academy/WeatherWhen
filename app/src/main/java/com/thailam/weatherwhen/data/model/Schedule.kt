package com.thailam.weatherwhen.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thailam.weatherwhen.data.model.Schedule.Companion.TBL_SCHEDULE_NAME

@Entity(tableName = TBL_SCHEDULE_NAME)
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TBL_SCHEDULE_ID)
    val id: Int,
    @ColumnInfo(name = TBL_SCHEDULE_EPOCH_DATE)
    val epochDate: Long,
    @ColumnInfo(name = TBL_SCHEDULE_NOTE)
    val note: String,
    @Embedded(prefix = TBL_SCHEDULE_PREFIX)
    val condition: CurrentCondition?
) {
    companion object {
        const val TBL_SCHEDULE_NAME = "schedule"
        const val TBL_SCHEDULE_ID = "sch_id"
        const val TBL_SCHEDULE_EPOCH_DATE = "sch_epoch_date"
        const val TBL_SCHEDULE_NOTE = "sch_note"
        const val TBL_SCHEDULE_PREFIX = "sch_condition_"
    }
}
