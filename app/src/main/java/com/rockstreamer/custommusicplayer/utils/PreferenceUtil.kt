package com.rockstreamer.custommusicplayer.utils

import android.preference.PreferenceManager
import com.rockstreamer.custommusicplayer.App
import com.rockstreamer.custommusicplayer.model.CategoryInfo

object PreferenceUtil {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getContext())

    val defaultCategories = listOf(
        CategoryInfo(CategoryInfo.Category.Home , true),

    )
}