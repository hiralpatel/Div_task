package com.hpandro.divtech_testtask.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import com.kaopiz.kprogresshud.KProgressHUD
import com.hpandro.divtech_testtask.R

class ProgressUtil {
    companion object {
        private var hud: KProgressHUD? = null

        var IMEI = ""
        var IMSI = ""

        fun showProgressDialog(context: Context) {
            if (hud == null) {
                hud = KProgressHUD(context)
                hud = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setBackgroundColor(Color.parseColor("#FF6200EE"))
                    .setLabel("Please wait...")
                    .setCancellable(false)
                    .show();
            }
        }

        fun dismissDialog() {
            if (hud != null) {
                if (hud!!.isShowing) {
                    hud!!.dismiss();
                    hud = null;
                }
            }
        }

        fun showAlert(context: Context, msg: String) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.app_name)
            builder.setMessage(msg)
            builder.setPositiveButton("OK") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
}