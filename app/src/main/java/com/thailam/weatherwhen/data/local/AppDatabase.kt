package com.thailam.weatherwhen.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thailam.weatherwhen.data.local.dao.DailyForecastDao
import com.thailam.weatherwhen.data.local.dao.ScheduleDao
import com.thailam.weatherwhen.data.model.DailyForecast
import com.thailam.weatherwhen.data.model.Schedule
import com.thailam.weatherwhen.data.model.Schedule.Companion.TBL_SCHEDULE_EPOCH_DATE
import com.thailam.weatherwhen.data.model.Schedule.Companion.TBL_SCHEDULE_ID
import com.thailam.weatherwhen.data.model.Schedule.Companion.TBL_SCHEDULE_NAME
import com.thailam.weatherwhen.data.model.Schedule.Companion.TBL_SCHEDULE_NOTE
import com.thailam.weatherwhen.data.model.Schedule.Companion.TBL_SCHEDULE_PREFIX


@Database(entities = [DailyForecast::class, Schedule::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyForecastDao(): DailyForecastDao
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        private const val DB_NAME = "weatherWhen.db"
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val INSERT_SCHEDULE_TBL_QUERY =
            "CREATE TABLE IF NOT EXISTS `$TBL_SCHEDULE_NAME` (`$TBL_SCHEDULE_ID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " +
                    "`$TBL_SCHEDULE_EPOCH_DATE` INTEGER NOT NULL, `$TBL_SCHEDULE_NOTE` TEXT NOT NULL, " +
                    "`sch_condition_icon` INTEGER, `sch_condition_phrase` TEXT, `sch_condition_rainProp` INTEGER, " +
                    "`sch_condition_rain_value` REAL, `sch_condition_rain_unit` TEXT, `sch_condition_wind_speed_value` REAL, " +
                    "`sch_condition_wind_speed_unit` TEXT, `sch_condition_wind_direction_englishDir` TEXT, " +
                    "`sch_condition_wind_direction_degrees` REAL )  "

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).addMigrations(MIGRATION_1_2).build()

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(INSERT_SCHEDULE_TBL_QUERY)
            }
        }
    }
}
