/*
 * Copyright (c) 2020 Hemanth Savarla.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 */
package com.rockstreamer.custommusicplayer.fragment

import android.animation.ValueAnimator
import android.widget.Toast
import androidx.lifecycle.*
import com.rockstreamer.custommusicplayer.App
import com.rockstreamer.custommusicplayer.db.*
import com.rockstreamer.custommusicplayer.helper.MusicPlayerRemote
import com.rockstreamer.custommusicplayer.interfaces.IMusicServiceEventListener
import com.rockstreamer.custommusicplayer.model.*
import com.rockstreamer.custommusicplayer.repository.RealRepository
import com.rockstreamer.custommusicplayer.utils.PreferenceUtil
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.logging.Filter

class LibraryViewModel(
    private val repository: RealRepository
) : ViewModel(), IMusicServiceEventListener {

    private val _paletteColor = MutableLiveData<Int>()
    private val home = MutableLiveData<List<Home>>()
    private val albums = MutableLiveData<List<Album>>()
    private val songs = MutableLiveData<List<Song>>()
    private val artists = MutableLiveData<List<Artist>>()
    private val playlists = MutableLiveData<List<PlaylistWithSongs>>()
    private val legacyPlaylists = MutableLiveData<List<Playlist>>()
    private val genres = MutableLiveData<List<Genre>>()
    private val searchResults = MutableLiveData<List<Any>>()
    private val fabMargin = MutableLiveData(0)
    val paletteColor: LiveData<Int> = _paletteColor

    init {
        loadLibraryContent()
    }

    private fun loadLibraryContent() = viewModelScope.launch(IO) {
        fetchHomeSections()
        fetchSongs()
        fetchAlbums()
        fetchArtists()
        fetchGenres()
        fetchPlaylists()
    }

    fun getSearchResult(): LiveData<List<Any>> = searchResults

    fun getSongs(): LiveData<List<Song>> {
        return songs
    }

    fun getAlbums(): LiveData<List<Album>> {
        return albums
    }

    fun getArtists(): LiveData<List<Artist>> {
        return artists
    }

    fun getPlaylists(): LiveData<List<PlaylistWithSongs>> {
        return playlists
    }

    fun getLegacyPlaylist(): LiveData<List<Playlist>> {
        return legacyPlaylists
    }

    fun getGenre(): LiveData<List<Genre>> {
        return genres
    }

    fun getHome(): LiveData<List<Home>> {
        return home
    }

    fun getFabMargin(): LiveData<Int> {
        return fabMargin
    }

    private fun fetchSongs() {
        viewModelScope.launch(IO) {
            songs.postValue(repository.allSongs())
        }
    }

    private fun fetchAlbums() {
        viewModelScope.launch(IO) {
            albums.postValue(repository.fetchAlbums())
        }
    }

    private fun fetchArtists() {
        if (PreferenceUtil.albumArtistsOnly) {
            viewModelScope.launch(IO) {
                artists.postValue(repository.albumArtists())
            }
        } else {
            viewModelScope.launch(IO) {
                artists.postValue(repository.fetchArtists())
            }
        }
    }

    private fun fetchPlaylists() {
        viewModelScope.launch(IO) {
            playlists.postValue(repository.fetchPlaylistWithSongs())
        }
    }

    private fun fetchLegacyPlaylist() {
        viewModelScope.launch(IO) {
            legacyPlaylists.postValue(repository.fetchLegacyPlaylist())
        }
    }

    private fun fetchGenres() {
        viewModelScope.launch(IO) {
            genres.postValue(repository.fetchGenres())
        }
    }

    fun fetchHomeSections() {
        viewModelScope.launch(IO) {
            home.postValue(repository.homeSections())
        }
    }

    fun search(query: String?, filter: Filter) {
        viewModelScope.launch(IO) {
            val result = repository.search(query, filter)
            searchResults.postValue(result)
        }
    }

    fun forceReload(reloadType: ReloadType) = viewModelScope.launch {
        when (reloadType) {
            ReloadType.Songs -> fetchSongs()
            ReloadType.Albums -> fetchAlbums()
            ReloadType.Artists -> fetchArtists()
            ReloadType.HomeSections -> fetchHomeSections()
            ReloadType.Playlists -> fetchPlaylists()
            ReloadType.Genres -> fetchGenres()
        }
    }

    fun updateColor(newColor: Int) {
        _paletteColor.postValue(newColor)
    }

    override fun onMediaStoreChanged() {
        println("onMediaStoreChanged")
        loadLibraryContent()
    }

    override fun onServiceConnected() {
        println("onServiceConnected")
    }

    override fun onServiceDisconnected() {
        println("onServiceDisconnected")
    }

    override fun onQueueChanged() {
        println("onQueueChanged")
    }

    override fun onPlayingMetaChanged() {
        println("onPlayingMetaChanged")
    }

    override fun onPlayStateChanged() {
        println("onPlayStateChanged")
    }

    override fun onRepeatModeChanged() {
        println("onRepeatModeChanged")
    }

    override fun onShuffleModeChanged() {
        println("onShuffleModeChanged")
    }

    override fun onFavoriteStateChanged() {
        println("onFavoriteStateChanged")
    }

    fun shuffleSongs() = viewModelScope.launch(IO) {
        val songs = repository.allSongs()
        MusicPlayerRemote.openAndShuffleQueue(
            songs,
            true
        )
    }

    fun renameRoomPlaylist(playListId: Long, name: String) = viewModelScope.launch(IO) {
        repository.renameRoomPlaylist(playListId, name)
    }

    fun deleteSongsInPlaylist(songs: List<SongEntity>) {
        viewModelScope.launch(IO) {
            repository.deleteSongsInPlaylist(songs)
            forceReload(ReloadType.Playlists)
        }
    }

    fun deleteSongsFromPlaylist(playlists: List<PlaylistEntity>) = viewModelScope.launch(IO) {
        repository.deletePlaylistSongs(playlists)
    }

    fun deleteRoomPlaylist(playlists: List<PlaylistEntity>) = viewModelScope.launch(IO) {
        repository.deleteRoomPlaylist(playlists)
    }

    fun albumById(id: Long) = repository.albumById(id)
    suspend fun artistById(id: Long) = repository.artistById(id)
    suspend fun favoritePlaylist() = repository.favoritePlaylist()
    suspend fun isFavoriteSong(song: SongEntity) = repository.isFavoriteSong(song)
    suspend fun isSongFavorite(songId: Long) = repository.isSongFavorite(songId)
    suspend fun insertSongs(songs: List<SongEntity>) = repository.insertSongs(songs)
    suspend fun removeSongFromPlaylist(songEntity: SongEntity) =
        repository.removeSongFromPlaylist(songEntity)

    private suspend fun checkPlaylistExists(playlistName: String): List<PlaylistEntity> =
        repository.checkPlaylistExists(playlistName)

    private suspend fun createPlaylist(playlistEntity: PlaylistEntity): Long =
        repository.createPlaylist(playlistEntity)

    fun importPlaylists() = viewModelScope.launch(IO) {
        val playlists = repository.fetchLegacyPlaylist()
        playlists.forEach { playlist ->
            val playlistEntity = repository.checkPlaylistExists(playlist.name).firstOrNull()
            if (playlistEntity != null) {
                val songEntities = playlist.getSongs().map {
                    it.toSongEntity(playlistEntity.playListId)
                }
                repository.insertSongs(songEntities)
            } else {
                if (playlist != Playlist.empty){
                    val playListId = createPlaylist(PlaylistEntity(playlistName = playlist.name))
                    val songEntities = playlist.getSongs().map {
                        it.toSongEntity(playListId)
                    }
                    repository.insertSongs(songEntities)
                }
            }
            forceReload(ReloadType.Playlists)
        }
    }

    fun deleteTracks(songs: List<Song>) = viewModelScope.launch(IO) {
        repository.deleteSongs(songs)
        fetchPlaylists()
        loadLibraryContent()
    }

    fun recentSongs(): LiveData<List<Song>> = liveData {
        emit(repository.recentSongs())
    }

    fun playCountSongs(): LiveData<List<Song>> = liveData {
        val songs = repository.playCountSongs().map {
            it.toSong()
        }
        emit(songs)
        // Cleaning up deleted or moved songs
        withContext(IO) {
            songs.forEach { song ->
                if (!File(song.data).exists() || song.id == -1L) {
                    repository.deleteSongInPlayCount(song.toPlayCount())
                }
            }
            emit(repository.playCountSongs().map {
                it.toSong()
            })
        }
    }

    fun artists(type: Int): LiveData<List<Artist>> = liveData {
        when (type) {
            TOP_ARTISTS -> emit(repository.topArtists())
            RECENT_ARTISTS -> {
                emit(repository.recentArtists())
            }
        }
    }

    fun albums(type: Int): LiveData<List<Album>> = liveData {
        when (type) {
            TOP_ALBUMS -> emit(repository.topAlbums())
            RECENT_ALBUMS -> {
                emit(repository.recentAlbums())
            }
        }
    }

    fun artist(artistId: Long): LiveData<Artist> = liveData {
        emit(repository.artistById(artistId))
    }

    fun fetchContributors(): LiveData<List<Contributor>> = liveData {
        emit(repository.contributor())
    }

    fun observableHistorySongs(): LiveData<List<Song>> = liveData {
        val songs = repository.historySong().map {
            it.toSong()
        }
        emit(songs)
        // Cleaning up deleted or moved songs
        songs.forEach { song ->
            if (!File(song.data).exists() || song.id == -1L) {
                repository.deleteSongInHistory(song.id)
            }
        }
        emit(repository.historySong().map {
            it.toSong()
        })
    }

    fun favorites() = repository.favorites()

    fun clearSearchResult() {
        viewModelScope.launch {
            searchResults.postValue(emptyList())
        }
    }

    fun addToPlaylist(playlistName: String, songs: List<Song>) {
        viewModelScope.launch(IO) {
            val playlists = checkPlaylistExists(playlistName)
            if (playlists.isEmpty()) {
                val playlistId: Long =
                    createPlaylist(PlaylistEntity(playlistName = playlistName))
                insertSongs(songs.map { it.toSongEntity(playlistId) })
                forceReload(ReloadType.Playlists)
            } else {
                val playlist = playlists.firstOrNull()
                if (playlist != null) {
                    insertSongs(songs.map {
                        it.toSongEntity(playListId = playlist.playListId)
                    })
                }
                withContext(Main) {
                    Toast.makeText(
                        App.getContext(),
                        "Playlist already exists",
                        Toast.LENGTH_SHORT
                    ).show()
                    if (songs.isNotEmpty()) {
                       Toast.makeText(
                            App.getContext(),
                            "Adding songs to $playlistName",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
    }

    fun setFabMargin(bottomMargin: Int) {
        val currentValue = DensityUtil.dip2px(App.getContext(), 16F) +
                bottomMargin
        if (currentValue != fabMargin.value) {
            ValueAnimator.ofInt(fabMargin.value!!, currentValue).apply {
                addUpdateListener {
                    fabMargin.postValue(
                        it.animatedValue as Int
                    )
                }
                start()
            }
        }
    }
}

enum class ReloadType {
    Songs,
    Albums,
    Artists,
    HomeSections,
    Playlists,
    Genres,
}
