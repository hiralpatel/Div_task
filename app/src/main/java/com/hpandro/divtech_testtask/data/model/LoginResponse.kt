package com.hpandro.divtech_testtask.data.model

data class LoginResponse(
    val errorCode: String,
    val errorMessage: String,
    val user: User
)