package com.rockstreamer.custommusicplayer.utils

import android.preference.PreferenceManager
import com.rockstreamer.custommusicplayer.App

object PreferenceUtil {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getContext())

    val defaultCategories = listOf(


    )
}