package com.rockstreamer.custommusicplayer.utils.theme

import android.content.Context
import androidx.annotation.StyleRes
import com.rockstreamer.custommusicplayer.utils.PreferenceUtil

object ThemeManager {


    @StyleRes
    fun getThemeResValue(
        context: Context
    ): Int = if (PreferenceUtil.ma)



}