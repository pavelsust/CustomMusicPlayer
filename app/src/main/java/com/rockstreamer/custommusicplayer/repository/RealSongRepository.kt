package com.rockstreamer.custommusicplayer.repository

import android.content.Context
import android.database.Cursor
import android.os.Environment
import android.provider.MediaStore
import com.rockstreamer.custommusicplayer.extensions.getInt
import com.rockstreamer.custommusicplayer.extensions.getLong
import com.rockstreamer.custommusicplayer.extensions.getString
import com.rockstreamer.custommusicplayer.extensions.getStringOrNull
import com.rockstreamer.custommusicplayer.model.Song
import com.rockstreamer.custommusicplayer.utils.PreferenceUtil
import java.util.ArrayList

interface SongRepository {

    fun songs(): List<Song>

    fun songs(cursor: Cursor?): List<Song>

    fun songs(query: String): List<Song>

    fun songsByFilePath(filePath: String): List<Song>

    fun song(cursor: Cursor?): Song

    fun song(songId: Long): Song
}

class RealSongRepository(private val context: Context) : SongRepository {

    override fun songs(): List<Song> {
        return songs(makeSongCursor(null, null))
    }

    override fun songs(cursor: Cursor?): List<Song> {
        val songs = arrayListOf<Song>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                songs.add(getSongFromCursorImpl(cursor))
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return songs
    }

    override fun song(cursor: Cursor?): Song {
        val song: Song = if (cursor != null && cursor.moveToFirst()) {
            getSongFromCursorImpl(cursor)
        } else {
            Song.emptySong
        }
        cursor?.close()
        return song
    }

    override fun songs(query: String): List<Song> {
        return songs(makeSongCursor(MediaStore.Audio.AudioColumns.TITLE + " LIKE ?", arrayOf("%$query%")))
    }

    override fun song(songId: Long): Song {
        return song(makeSongCursor(MediaStore.Audio.AudioColumns._ID + "=?", arrayOf(songId.toString())))
    }

    override fun songsByFilePath(filePath: String): List<Song> {
        return songs(
            makeSongCursor(
                MediaStore.Audio.AudioColumns.DATA + "=?",
                arrayOf(filePath)
            )
        )
    }

    private fun getSongFromCursorImpl(
        cursor: Cursor
    ): Song {
        val id = cursor.getLong(MediaStore.Audio.AudioColumns._ID)
        val title = cursor.getString(MediaStore.Audio.AudioColumns.TITLE)
        val trackNumber = cursor.getInt(MediaStore.Audio.AudioColumns.TRACK)
        val year = cursor.getInt(MediaStore.Audio.AudioColumns.YEAR)
        val duration = cursor.getLong(MediaStore.Audio.AudioColumns.DURATION)
        val data = cursor.getString(MediaStore.Audio.AudioColumns.DATA)
        val dateModified = cursor.getLong(MediaStore.Audio.AudioColumns.DATE_MODIFIED)
        val albumId = cursor.getLong(MediaStore.Audio.AudioColumns.ALBUM_ID)
        val albumName = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.ALBUM)
        val artistId = cursor.getLong(MediaStore.Audio.AudioColumns.ARTIST_ID)
        val artistName = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.ARTIST)
        val composer = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.COMPOSER)
        val albumArtist = cursor.getStringOrNull("album_artist")
        return Song(
            id,
            title,
            trackNumber,
            year,
            duration,
            data,
            dateModified,
            albumId,
            albumName ?: "",
            artistId,
            artistName ?: "",
            composer ?: "",
            albumArtist ?: ""
        )
    }

    @JvmOverloads
    fun makeSongCursor(

        selection: String?,
        selectionValues: Array<String>?,
        sortOrder: String = PreferenceUtil.songSortOrder
    ): Cursor? {
        var selectionFinal = selection
        var selectionValuesFinal = selectionValues
        selectionFinal = if (selection != null && selection.trim { it <= ' ' } != "") {
            "$IS_MUSIC AND $selectionFinal"
        } else {
            IS_MUSIC
        }

        // Whitelist
        if (PreferenceUtil.isWhiteList) {
            selectionFinal =
                selectionFinal + " AND " + MediaStore.Audio.AudioColumns.DATA + " LIKE ?"
            selectionValuesFinal = addSelectionValues(
                selectionValuesFinal, arrayListOf(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).canonicalPath
                )
            )
        } else {
            // Blacklist
            val paths = BlacklistStore.getInstance(context).paths
            if (paths.isNotEmpty()) {
                selectionFinal = generateBlacklistSelection(selectionFinal, paths.size)
                selectionValuesFinal = addSelectionValues(selectionValuesFinal, paths)
            }
        }

        selectionFinal =
            selectionFinal + " AND " + MediaStore.Audio.Media.DURATION + ">= " + (PreferenceUtil.filterLength * 1000)

        val uri = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }
        return try {
            context.contentResolver.query(
                uri,
                baseProjection,
                selectionFinal,
                selectionValuesFinal,
                sortOrder
            )
        } catch (ex: SecurityException) {
            return null
        }
    }

    private fun generateBlacklistSelection(
        selection: String?,
        pathCount: Int
    ): String {
        val newSelection = StringBuilder(
            if (selection != null && selection.trim { it <= ' ' } != "") "$selection AND " else "")
        newSelection.append(MediaStore.Audio.AudioColumns.DATA + " NOT LIKE ?")
        for (i in 0 until pathCount - 1) {
            newSelection.append(" AND " + MediaStore.Audio.AudioColumns.DATA + " NOT LIKE ?")
        }
        return newSelection.toString()
    }

    private fun addSelectionValues(
        selectionValues: Array<String>?,
        paths: ArrayList<String>
    ): Array<String> {
        var selectionValuesFinal = selectionValues
        if (selectionValuesFinal == null) {
            selectionValuesFinal = emptyArray()
        }
        val newSelectionValues = Array(selectionValuesFinal.size + paths.size) {
            "n = $it"
        }
        System.arraycopy(selectionValuesFinal, 0, newSelectionValues, 0, selectionValuesFinal.size)
        for (i in selectionValuesFinal.size until newSelectionValues.size) {
            newSelectionValues[i] = paths[i - selectionValuesFinal.size] + "%"
        }
        return newSelectionValues
    }
}
