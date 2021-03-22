package com.hpandro.divtech_testtask.data.model.dbfields

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "userTable")
data class UserDetails(
    @PrimaryKey() var xAcc: String, var userId: String, var userName: String
) : Serializable