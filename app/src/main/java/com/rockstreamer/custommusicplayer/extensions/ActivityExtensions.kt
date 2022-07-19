package com.rockstreamer.custommusicplayer.extensions

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import code.name.monkey.appthemehelper.util.ToolbarContentTintHelper
import com.google.android.material.appbar.MaterialToolbar


fun AppCompatActivity.applyToolbar(toolbar: MaterialToolbar){
    ToolbarContentTintHelper.colorBackButton(toolbar)
    setSupportActionBar(toolbar)
}



inline fun <reified  T: Any> Activity.extra(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}

inline fun <reified T : Any> Activity.extraNotNull(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    requireNotNull(if (value is T) value else default) { key }
}