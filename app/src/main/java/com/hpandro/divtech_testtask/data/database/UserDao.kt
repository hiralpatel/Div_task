package com.hpandro.divtech_testtask.data.database

import androidx.room.*
import com.hpandro.divtech_testtask.data.model.dbfields.UserDetails

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: UserDetails)

    @Query("Select * from userTable")
    fun getAllDetails(): UserDetails
}