package com.rockstreamer.custommusicplayer.activity.base

import android.os.Bundle
import android.os.Handler
import code.name.monkey.appthemehelper.common.ATHToolbarActivity
import com.rockstreamer.custommusicplayer.utils.theme.ThemeManager

abstract class AbsThemeActivity: ATHToolbarActivity(), Runnable {
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    private fun updateTheme(){
        setTheme(ThemeManager.get)
    }
}