package com.rockstreamer.custommusicplayer.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class AbsMusicServiceFragment(@LayoutRes layout:Int):Fragment(layout) {

    val navOptions by lazy {
        navOptions{

        }
    }
}