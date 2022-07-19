package com.rockstreamer.custommusicplayer.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rockstreamer.custommusicplayer.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryInfo(
    val category: Category,
    @get:JvmName("isVisible")
    var visible: Boolean
) : Parcelable {

    enum class Category(
        val id: Int,
        @StringRes val stringRes: Int,
        @DrawableRes val icon: Int
    ) {
        Home(R.id.action_home, R.string.for_you, R.drawable.asld_face),
        Songs(R.id.action_song, R.string.songs, R.drawable.asld_music_note),
        Albums(R.id.action_album, R.string.albums, R.drawable.asld_album),
        Artists(R.id.action_artist, R.string.artists, R.drawable.asld_artist),
        Playlists(R.id.action_playlist, R.string.playlists, R.drawable.asld_playlist),
        Genres(R.id.action_genre, R.string.genres, R.drawable.asld_guitar)
    }
}