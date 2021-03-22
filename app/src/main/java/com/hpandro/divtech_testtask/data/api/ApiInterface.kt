package com.hpandro.divtech_testtask.data.api

import com.hpandro.divtech_testtask.data.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @POST("/api/login")
    fun userLogin(
        @Body body: HashMap<String, String>
    ): Call<LoginResponse>
}