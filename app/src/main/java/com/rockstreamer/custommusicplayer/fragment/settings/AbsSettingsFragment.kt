package com.rockstreamer.custommusicplayer.fragment.settings

import android.widget.Toast
import code.name.monkey.appthemehelper.common.prefs.supportv7.ATEPreferenceFragmentCompat

abstract class AbsSettingsFragment : ATEPreferenceFragmentCompat() {

    internal fun showProToastAndNavigate(message:String){
        Toast.makeText(requireActivity() , "${message} is Pro version feature", Toast.LENGTH_SHORT).show()
        NavigationUtil
    }
}