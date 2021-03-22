package com.hpandro.divtech_testtask.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hpandro.divtech_testtask.R
import com.hpandro.divtech_testtask.data.viewmodel.MainActivityViewModel
import com.hpandro.divtech_testtask.utils.ProgressUtil
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.reflect.InvocationTargetException

class LoginActivity : AppCompatActivity() {

    lateinit var context: Context
    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this@LoginActivity
        init()
    }

    @SuppressLint("NewApi")
    private fun init() {
        getIMEINumber()
        getIMSINumber()
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        btnLogin.setOnClickListener {
            if (edtUserName.text.toString().isEmpty()) {
                tvErrorUsername.visibility = View.VISIBLE
            } else {
                tvErrorUsername.visibility = View.GONE
            }

            if (edtPassword.text.toString().isEmpty()) {
                tvErrorPassword.visibility = View.VISIBLE
            } else {
                tvErrorPassword.visibility = View.GONE
            }

            mainActivityViewModel.getUser(
                edtUserName.text.toString(),
                edtPassword.text.toString(),
                this@LoginActivity
            )!!.observe(this, Observer { serviceSetterGetter ->
                val errorCode = serviceSetterGetter.errorCode
                if (errorCode == "00") {
                    val mainIntent = Intent(this, DetailsActivity::class.java)
                    startActivity(mainIntent)
                    overridePendingTransition(0, 0)
                    finish()
                }
            })
        }
    }

    private fun getIMEINumber() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_PHONE_STATE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    ProgressUtil.IMEI = getDeviceId(this@LoginActivity).toString()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                }
            }).check()
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String? {
        val deviceId: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } else {
            val mTelephony = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            if (mTelephony.deviceId != null) {
                mTelephony.deviceId
            } else {
                Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            }
        }
        return deviceId
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @SuppressLint("NewApi")
    fun getIMSINumber() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_PHONE_STATE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    var imsi: String? = "Unknown"
                    val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE)
                    try {
                        val getSubId: java.lang.reflect.Method =
                            TelephonyManager::class.java.getMethod(
                                "getSubscriberId",
                                Int::class.javaPrimitiveType
                            )
                        val sm =
                            getSystemService(TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
                        if (ActivityCompat.checkSelfPermission(
                                this@LoginActivity,
                                Manifest.permission.READ_PHONE_STATE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return
                        }
                        imsi = getSubId.invoke(
                            telephonyManager,
                            sm.getActiveSubscriptionInfoForSimSlotIndex(0).subscriptionId
                        ) as String // Sim slot 1 IMSI
                        ProgressUtil.IMSI = imsi
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    } catch (e: InvocationTargetException) {
                        e.printStackTrace()
                    } catch (e: NoSuchMethodException) {
                        e.printStackTrace()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                }
            }).check()
    }
}