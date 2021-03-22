package com.hpandro.divtech_testtask.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hpandro.divtech_testtask.activities.LoginActivity
import com.hpandro.divtech_testtask.data.model.LoginResponse
import com.hpandro.divtech_testtask.data.repository.MainActivityRepository

class MainActivityViewModel : ViewModel() {

    var servicesLiveData: MutableLiveData<LoginResponse>? = null

    fun getUser(username: String, password: String, loginActivity: LoginActivity): LiveData<LoginResponse>? {
        servicesLiveData = MainActivityRepository.userLogin(username, password, loginActivity)
        return servicesLiveData
    }

}