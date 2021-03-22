package com.hpandro.divtech_testtask.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hpandro.divtech_testtask.data.model.dbfields.UserDetails

@Database(
    entities = [UserDetails::class],
    version = 1,
    exportSchema = false
)
abstract class TestRoomDB : RoomDatabase() {
    abstract fun packageDao(): UserDao?

    companion object {
        private const val DB_NAME = "UserDetails"
        private var instance: TestRoomDB? = null

        @Synchronized
        fun getInstance(context: Context): TestRoomDB? {
            if (instance == null) instance =
                Room.databaseBuilder(
                    context.applicationContext,
                    TestRoomDB::class.java, DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            return instance
        }
    }
}