package com.rockstreamer.custommusicplayer.utils.theme

import android.content.Context
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDelegate
import com.rockstreamer.custommusicplayer.R
import com.rockstreamer.custommusicplayer.extensions.generalThemeValue
import com.rockstreamer.custommusicplayer.utils.PreferenceUtil
import com.rockstreamer.custommusicplayer.utils.ThemeMode


object ThemeManager {

    @StyleRes
    fun getThemeResValue(
        context: Context
    ): Int =
        if (PreferenceUtil.materialYou) {
            R.style.Theme_RetroMusic_MD3
        } else {
            when (context.generalThemeValue) {
                ThemeMode.LIGHT -> R.style.Theme_RetroMusic_Light
                ThemeMode.DARK -> R.style.Theme_RetroMusic_Base
                ThemeMode.BLACK -> R.style.Theme_RetroMusic_Black
                ThemeMode.AUTO -> R.style.Theme_RetroMusic_FollowSystem
            }
        }

    fun getNightMode(
        context: Context
    ): Int = when (context.generalThemeValue) {
        ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
        ThemeMode.BLACK -> {
            if (PreferenceUtil.baseTheme == "dark") {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        }
        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }
}