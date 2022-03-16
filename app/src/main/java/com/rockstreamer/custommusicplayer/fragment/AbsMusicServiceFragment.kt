package com.rockstreamer.custommusicplayer.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions
import com.rockstreamer.custommusicplayer.R

open class AbsMusicServiceFragment(@LayoutRes layout:Int):Fragment(layout) {

    val navOptions by lazy {
        navOptions {
            launchSingleTop = false
            anim {
                enter = R.anim.retro_fragment_open_enter
                exit = R.anim.retro_fragment_open_exit
                popEnter = R.anim.retro_fragment_close_enter
                popExit = R.anim.retro_fragment_close_exit
            }
        }
    }
}