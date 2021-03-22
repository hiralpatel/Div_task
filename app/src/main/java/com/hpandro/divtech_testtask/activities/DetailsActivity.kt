package com.hpandro.divtech_testtask.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hpandro.divtech_testtask.R
import com.hpandro.divtech_testtask.data.database.TestRoomDB
import com.hpandro.divtech_testtask.data.model.dbfields.UserDetails
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    private var details: UserDetails? = null
    private var appDB: TestRoomDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        init()
    }

    @SuppressLint("SetTextI18n")
    fun init() {
        appDB = TestRoomDB.getInstance(this@DetailsActivity)!!
        details = appDB!!.packageDao()!!.getAllDetails()

        tvUserID.text = "User ID: " + details!!.userId
        tvUserName.text = "User Name: " + details!!.userName
        tvXAcc.text = "X-Acc: " + details!!.xAcc
    }
}