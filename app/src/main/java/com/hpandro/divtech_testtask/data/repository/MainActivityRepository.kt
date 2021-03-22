package com.hpandro.divtech_testtask.data.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.hpandro.divtech_testtask.activities.LoginActivity
import com.hpandro.divtech_testtask.data.api.ApiClient
import com.hpandro.divtech_testtask.data.api.ApiInterface
import com.hpandro.divtech_testtask.data.database.TestRoomDB
import com.hpandro.divtech_testtask.data.model.LoginResponse
import com.hpandro.divtech_testtask.data.model.dbfields.UserDetails
import com.hpandro.divtech_testtask.utils.ProgressUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MainActivityRepository {
    fun userLogin(
        username: String,
        password: String,
        loginActivity: LoginActivity
    ): MutableLiveData<LoginResponse> {

        lateinit var appDB: TestRoomDB
        val mLoginResponse = MutableLiveData<LoginResponse>()
        val params = hashMapOf(
            "username" to username,
            "password" to password
        )
        val call = ApiClient.client.create<ApiInterface>(ApiInterface::class.java)
            .userLogin(params)
        ProgressUtil.showProgressDialog(loginActivity)
        call.enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                appDB = TestRoomDB.getInstance(loginActivity)!!
                val data = response.body()

                // Room database insertion
                mLoginResponse.value = data
                var userDetails = UserDetails(
                    response.headers()["X-Acc"].toString(),
                    response.body()!!.user.userId,
                    response.body()!!.user.userName
                )
                appDB.packageDao()!!.insertAll(userDetails)

                ProgressUtil.dismissDialog()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("error", t.message!!)
                ProgressUtil.dismissDialog()
                Toast.makeText(loginActivity, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }

        })
        return mLoginResponse
    }
}