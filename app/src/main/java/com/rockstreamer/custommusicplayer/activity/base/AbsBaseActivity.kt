package com.rockstreamer.custommusicplayer.activity.base

import android.graphics.Rect
import android.media.AudioManager
import android.os.Bundle
import code.name.monkey.appthemehelper.common.ATHToolbarActivity
import android.os.Handler
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

abstract class AbsBaseActivity : AbsThemeActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        volumeControlStream = AudioManager.STREAM_MUSIC
    }
}