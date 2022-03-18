package com.rockstreamer.custommusicplayer.utils

import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rockstreamer.custommusicplayer.*
import com.rockstreamer.custommusicplayer.extensions.getStringOrDefault
import com.rockstreamer.custommusicplayer.helper.SortOrder
import com.rockstreamer.custommusicplayer.model.CategoryInfo

object PreferenceUtil {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getContext())


    fun registerOnSharedPreferenceChangedListener(listener: SharedPreferences.OnSharedPreferenceChangeListener
    ) = sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

    fun unregisterOnSharedPreferenceChangedListener(
        changeListener: SharedPreferences.OnSharedPreferenceChangeListener
    ) = sharedPreferences.unregisterOnSharedPreferenceChangeListener(changeListener)

    val baseTheme get() = sharedPreferences.getStringOrDefault(GENERAL_THEME, "auto")
    val languageCode: String get() = sharedPreferences.getString(LANGUAGE_NAME, "auto") ?: "auto"

    var userName
        get() = sharedPreferences.getString(
            USER_NAME,
            App.getContext().getString(R.string.user_name)
        )
        set(value) = sharedPreferences.edit {
            putString(USER_NAME, value)
        }

    var safSdCardUri
        get() = sharedPreferences.getStringOrDefault(SAF_SDCARD_URI, "")
        set(value) = sharedPreferences.edit {
            putString(SAF_SDCARD_URI, value)
        }

    val autoDownloadImagesPolicy
        get() = sharedPreferences.getStringOrDefault(
            AUTO_DOWNLOAD_IMAGES_POLICY,
            "only_wifi"
        )

    var albumArtistsOnly
        get() = sharedPreferences.getBoolean(
            ALBUM_ARTISTS_ONLY,
            false
        )
        set(value) = sharedPreferences.edit { putBoolean(ALBUM_ARTISTS_ONLY, value) }

    var albumDetailSongSortOrder
        get() = sharedPreferences.getStringOrDefault(
            ALBUM_DETAIL_SONG_SORT_ORDER,
            SortOrder.AlbumSongSortOrder.SONG_TRACK_LIST
        )
        set(value) = sharedPreferences.edit { putString(ALBUM_DETAIL_SONG_SORT_ORDER, value) }

    var artistDetailSongSortOrder
        get() = sharedPreferences.getStringOrDefault(
            ARTIST_DETAIL_SONG_SORT_ORDER,
            SortOrder.ArtistSongSortOrder.SONG_A_Z
        )
        set(value) = sharedPreferences.edit { putString(ARTIST_DETAIL_SONG_SORT_ORDER, value) }

    var songSortOrder
        get() = sharedPreferences.getStringOrDefault(
            SONG_SORT_ORDER,
            SortOrder.SongSortOrder.SONG_A_Z
        )
        set(value) = sharedPreferences.edit {
            putString(SONG_SORT_ORDER, value)
        }

    var albumSortOrder
        get() = sharedPreferences.getStringOrDefault(
            ALBUM_SORT_ORDER,
            SortOrder.AlbumSortOrder.ALBUM_A_Z
        )
        set(value) = sharedPreferences.edit {
            putString(ALBUM_SORT_ORDER, value)
        }

    var artistSortOrder
        get() = sharedPreferences.getStringOrDefault(
            ARTIST_SORT_ORDER,
            SortOrder.ArtistSortOrder.ARTIST_A_Z
        )
        set(value) = sharedPreferences.edit {
            putString(ARTIST_SORT_ORDER, value)
        }

    val albumSongSortOrder
        get() = sharedPreferences.getStringOrDefault(
            ALBUM_SONG_SORT_ORDER,
            SortOrder.AlbumSongSortOrder.SONG_TRACK_LIST
        )

    val artistSongSortOrder
        get() = sharedPreferences.getStringOrDefault(
            ARTIST_SONG_SORT_ORDER,
            SortOrder.AlbumSongSortOrder.SONG_TRACK_LIST
        )

    val artistAlbumSortOrder
        get() = sharedPreferences.getStringOrDefault(
            ARTIST_ALBUM_SORT_ORDER,
            SortOrder.ArtistAlbumSortOrder.ALBUM_A_Z
        )

    var playlistSortOrder
        get() = sharedPreferences.getStringOrDefault(
            PLAYLIST_SORT_ORDER,
            SortOrder.PlaylistSortOrder.PLAYLIST_A_Z
        )
        set(value) = sharedPreferences.edit {
            putString(PLAYLIST_SORT_ORDER, value)
        }

    val genreSortOrder
        get() = sharedPreferences.getStringOrDefault(
            GENRE_SORT_ORDER,
            SortOrder.GenreSortOrder.GENRE_A_Z
        )

    val isIgnoreMediaStoreArtwork
        get() = sharedPreferences.getBoolean(
            IGNORE_MEDIA_STORE_ARTWORK,
            false
        )

    val isVolumeVisibilityMode
        get() = sharedPreferences.getBoolean(
            TOGGLE_VOLUME, false
        )

    var isInitializedBlacklist
        get() = sharedPreferences.getBoolean(
            INITIALIZED_BLACKLIST, false
        )
        set(value) = sharedPreferences.edit {
            putBoolean(INITIALIZED_BLACKLIST, value)
        }

    private val isBlackMode
        get() = sharedPreferences.getBoolean(
            BLACK_THEME, false
        )

    val isExtraControls
        get() = sharedPreferences.getBoolean(
            TOGGLE_ADD_CONTROLS, false
        )

    val isHomeBanner
        get() = sharedPreferences.getBoolean(
            TOGGLE_HOME_BANNER, false
        )

    var isClassicNotification
        get() = sharedPreferences.getBoolean(CLASSIC_NOTIFICATION, false)
        set(value) = sharedPreferences.edit { putBoolean(CLASSIC_NOTIFICATION, value) }

    val isScreenOnEnabled get() = sharedPreferences.getBoolean(KEEP_SCREEN_ON, false)

    val isShuffleModeOn get() = sharedPreferences.getBoolean(TOGGLE_SHUFFLE, false)

    val isSongInfo get() = sharedPreferences.getBoolean(EXTRA_SONG_INFO, false)

    val isPauseOnZeroVolume get() = sharedPreferences.getBoolean(PAUSE_ON_ZERO_VOLUME, false)

    var isSleepTimerFinishMusic
        get() = sharedPreferences.getBoolean(
            SLEEP_TIMER_FINISH_SONG, false
        )
        set(value) = sharedPreferences.edit {
            putBoolean(SLEEP_TIMER_FINISH_SONG, value)
        }

    val isExpandPanel get() = sharedPreferences.getBoolean(EXPAND_NOW_PLAYING_PANEL, false)

    val isHeadsetPlugged
        get() = sharedPreferences.getBoolean(
            TOGGLE_HEADSET, false
        )

    val isAlbumArtOnLockScreen
        get() = sharedPreferences.getBoolean(
            ALBUM_ART_ON_LOCK_SCREEN, false
        )

    val isAudioDucking
        get() = sharedPreferences.getBoolean(
            AUDIO_DUCKING, true
        )

    val isBluetoothSpeaker
        get() = sharedPreferences.getBoolean(
            BLUETOOTH_PLAYBACK, false
        )

    val isBlurredAlbumArt
        get() = sharedPreferences.getBoolean(
            BLURRED_ALBUM_ART, false
        )

    val blurAmount get() = sharedPreferences.getInt(NEW_BLUR_AMOUNT, 25)

    val isCarouselEffect
        get() = sharedPreferences.getBoolean(
            CAROUSEL_EFFECT, false
        )

    var isColoredAppShortcuts
        get() = sharedPreferences.getBoolean(
            COLORED_APP_SHORTCUTS, true
        )
        set(value) = sharedPreferences.edit {
            putBoolean(COLORED_APP_SHORTCUTS, value)
        }

    var isColoredNotification
        get() = sharedPreferences.getBoolean(
            COLORED_NOTIFICATION, true
        )
        set(value) = sharedPreferences.edit {
            putBoolean(COLORED_NOTIFICATION, value)
        }

    var isDesaturatedColor
        get() = sharedPreferences.getBoolean(
            DESATURATED_COLOR, false
        )
        set(value) = sharedPreferences.edit {
            putBoolean(DESATURATED_COLOR, value)
        }

    val isGapLessPlayback
        get() = sharedPreferences.getBoolean(
            GAP_LESS_PLAYBACK, false
        )

    val isAdaptiveColor
        get() = sharedPreferences.getBoolean(
            ADAPTIVE_COLOR_APP, false
        )

    val isFullScreenMode
        get() = sharedPreferences.getBoolean(
            TOGGLE_FULL_SCREEN, false
        )

    val isLockScreen get() = sharedPreferences.getBoolean(LOCK_SCREEN, false)

    fun isAllowedToDownloadMetadata(): Boolean {
        return when (autoDownloadImagesPolicy) {
            "always" -> true
            "only_wifi" -> {
                val connectivityManager = ContextCompat.getSystemService(
                    App.getContext(),
                    ConnectivityManager::class.java
                )
                var netInfo: NetworkInfo? = null
                if (connectivityManager != null) {
                    netInfo = connectivityManager.activeNetworkInfo
                }
                netInfo != null && netInfo.type == ConnectivityManager.TYPE_WIFI && netInfo.isConnectedOrConnecting
            }
            "never" -> false
            else -> false
        }
    }
    var lyricsOption
        get() = sharedPreferences.getInt(LYRICS_OPTIONS, 1)
        set(value) = sharedPreferences.edit {
            putInt(LYRICS_OPTIONS, value)
        }

    var songGridStyle: GridStyle
        get() {
            val id: Int = sharedPreferences.getInt(SONG_GRID_STYLE, 0)
            // We can directly use "first" kotlin extension function here but
            // there maybe layout id stored in this so to avoid a crash we use
            // "firstOrNull"
            return GridStyle.values().firstOrNull { gridStyle ->
                gridStyle.id == id
            } ?: GridStyle.Grid
        }
        set(value) = sharedPreferences.edit {
            putInt(SONG_GRID_STYLE, value.id)
        }

}