package com.rockstreamer.custommusicplayer.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat
import com.rockstreamer.custommusicplayer.activity.PurchaseActivity

object NavigationUtil {

    fun bugReport(activity: Activity){

    }

    fun goToProVersion(context: Context){
        ActivityCompat.startActivity(context , Intent(context , PurchaseActivity::class.java), null)
    }

    private fun stockEqualizer(activity: Activity){
        val sessionId = audioSee
    }
}