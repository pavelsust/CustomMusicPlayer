package com.rockstreamer.custommusicplayer.activity

import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.ColorCallback
import com.rockstreamer.custommusicplayer.activity.base.AbsThemeActivity
import com.rockstreamer.custommusicplayer.interfaces.OnThemeChangedListener

class SettingsActivity: AbsThemeActivity(), ColorCallback , OnThemeChangedListener {

    private lateinit var binding: Ac

    override fun run() {
        TODO("Not yet implemented")
    }

    override fun onThemeValuesChanged() {
        TODO("Not yet implemented")
    }

    override fun invoke(dialog: MaterialDialog, color: Int) {
        TODO("Not yet implemented")
    }
}