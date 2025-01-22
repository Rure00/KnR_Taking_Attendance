package com.rure.knr_takingattendance.presentation.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

class RequestPermission(
    private val activity: Activity,
    private val context: Context
) {
    private val callPermissionCode = 1000
    private val callPermissions =  arrayOf(Manifest.permission.CALL_PHONE)

    fun checkCallPermission() = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED

    fun requestCall(telNum: String) {
        if (checkCallPermission()) {
            ActivityCompat.requestPermissions(activity, callPermissions, callPermissionCode);
        } else {
            val callIntent = Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:$telNum"));
            context.startActivity(callIntent);
        }
    }
}