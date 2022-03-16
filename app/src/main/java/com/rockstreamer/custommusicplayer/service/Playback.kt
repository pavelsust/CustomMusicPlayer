package com.rockstreamer.custommusicplayer.service

interface Playback {

    val isInitialized: Boolean

    val isPlaying: Boolean

    val audioSessionId: Int

    fun setDataSource(path: String): Boolean

    fun setNextDataSource(path: String?)

    fun setCallbacks(callbacks: PlaybackCallbacks)

    fun start(): Boolean

    fun stop()

    fun release()

    fun pause(): Boolean

    fun duration(): Int

    fun position(): Int

    fun seek(whereto: Int): Int

    fun setVolume(vol: Float): Boolean

    fun setAudioSessionId(sessionId: Int): Boolean

    fun setCrossFadeDuration(duration: Int)

    interface PlaybackCallbacks {
        fun onTrackWentToNext()

        fun onTrackEnded()

        fun onTrackEndedWithCrossfade()
    }
}