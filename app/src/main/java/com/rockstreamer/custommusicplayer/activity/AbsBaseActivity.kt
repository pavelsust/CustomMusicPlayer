package com.rockstreamer.custommusicplayer.activity

import android.os.Bundle
import code.name.monkey.appthemehelper.common.ATHToolbarActivity
import android.os.Handler

abstract class AbsBaseActivity: ATHToolbarActivity() , Runnable {

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

}