package com.rockstreamer.custommusicplayer.activity.base

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.core.os.ConfigurationCompat
import code.name.monkey.appthemehelper.common.ATHToolbarActivity
import com.google.android.material.color.DynamicColors
import com.rockstreamer.custommusicplayer.LanguageContextWrapper
import com.rockstreamer.custommusicplayer.R
import com.rockstreamer.custommusicplayer.extensions.*
import com.rockstreamer.custommusicplayer.utils.PreferenceUtil
import com.rockstreamer.custommusicplayer.utils.theme.ThemeManager
import java.util.*

abstract class AbsThemeActivity: ATHToolbarActivity(), Runnable {
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        setDrawBehindSystemBars()
        updateTheme()
        hideStatusBar()
        super.onCreate(savedInstanceState)
        setImmersiveFullscreen()
        registerSystemUiVisibility()
        toggleScreenOn()
        setLightNavigationAuto()
        setLightStatusBarAuto(surfaceColor())
    }

    private fun updateTheme() {
        setTheme(ThemeManager.getThemeResValue(this))
        setDefaultNightMode(ThemeManager.getNightMode(this))


        // Apply dynamic colors to activity if enabled
        if (PreferenceUtil.materialYou) {
            DynamicColors.applyIfAvailable(
                this,
                R.style.Theme_RetroMusic_Light
            )
        }
    }

    private fun registerSystemUiVisibility() {
        val decorView = window.decorView
        decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                setImmersiveFullscreen()
            }
        }
    }

    private fun unregisterSystemUiVisibility() {
        val decorView = window.decorView
        decorView.setOnSystemUiVisibilityChangeListener(null)
    }

    override fun run() {
        setImmersiveFullscreen()
    }

    override fun onStop() {
        handler.removeCallbacks(this)
        super.onStop()
    }

    public override fun onDestroy() {
        super.onDestroy()
        unregisterSystemUiVisibility()
        exitFullscreen()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            handler.removeCallbacks(this)
            handler.postDelayed(this, 500)
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun attachBaseContext(newBase: Context?) {
        val code = PreferenceUtil.languageCode
        val locale = if (code == "auto") {
            // Get the device default locale
            ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
        } else {
            Locale.forLanguageTag(code)
        }
        super.attachBaseContext(LanguageContextWrapper.wrap(newBase, locale))
    }


}