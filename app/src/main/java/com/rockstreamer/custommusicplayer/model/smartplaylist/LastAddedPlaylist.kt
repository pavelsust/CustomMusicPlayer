package com.rockstreamer.custommusicplayer.model.smartplaylist

import code.name.monkey.retromusic.model.smartplaylist.AbsSmartPlaylist
import com.rockstreamer.custommusicplayer.App
import com.rockstreamer.custommusicplayer.R
import com.rockstreamer.custommusicplayer.model.Song
import kotlinx.parcelize.Parcelize

@Parcelize
class LastAddedPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.last_added),
    iconRes = R.drawable.ic_library_add
) {
    override fun songs(): List<Song> {
        return lastAddedRepository.recentSongs()
    }
}