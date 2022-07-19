package com.rockstreamer.custommusicplayer.activity.base

import com.rockstreamer.custommusicplayer.interfaces.IMusicServiceEventListener
import java.util.ArrayList

abstract class AbsMusicServiceActivity : AbsBaseActivity() , IMusicServiceEventListener{

    private val mMusicServiceEventListeners = ArrayList<IMusicServiceEventListener>()


}