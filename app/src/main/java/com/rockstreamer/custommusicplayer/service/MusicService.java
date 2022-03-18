package com.rockstreamer.custommusicplayer.service;

import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.PlaybackStateCompat;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;

import com.rockstreamer.custommusicplayer.utils.Constant;
import com.rockstreamer.custommusicplayer.utils.PackageValidator;

import java.util.List;

public class MusicService extends MediaBrowserServiceCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Playback.PlaybackCallbacks {

    public static final String TAG = MusicService.class.getSimpleName();
    public static final String RETRO_MUSIC_PACKAGE_NAME = Constant.PACKAGE_NAME;
    public static final String MUSIC_PACKAGE_NAME = "com.android.music";
    public static final String ACTION_TOGGLE_PAUSE = RETRO_MUSIC_PACKAGE_NAME + ".togglepause";
    public static final String ACTION_PLAY = RETRO_MUSIC_PACKAGE_NAME + ".play";
    public static final String ACTION_PLAY_PLAYLIST = RETRO_MUSIC_PACKAGE_NAME + ".play.playlist";
    public static final String ACTION_PAUSE = RETRO_MUSIC_PACKAGE_NAME + ".pause";
    public static final String ACTION_STOP = RETRO_MUSIC_PACKAGE_NAME + ".stop";
    public static final String ACTION_SKIP = RETRO_MUSIC_PACKAGE_NAME + ".skip";
    public static final String ACTION_REWIND = RETRO_MUSIC_PACKAGE_NAME + ".rewind";
    public static final String ACTION_QUIT = RETRO_MUSIC_PACKAGE_NAME + ".quitservice";
    public static final String ACTION_PENDING_QUIT = RETRO_MUSIC_PACKAGE_NAME + ".pendingquitservice";
    public static final String INTENT_EXTRA_PLAYLIST = RETRO_MUSIC_PACKAGE_NAME + "intentextra.playlist";
    public static final String INTENT_EXTRA_SHUFFLE_MODE = RETRO_MUSIC_PACKAGE_NAME + ".intentextra.shufflemode";
    public static final String APP_WIDGET_UPDATE = RETRO_MUSIC_PACKAGE_NAME + ".appwidgetupdate";
    public static final String EXTRA_APP_WIDGET_NAME = RETRO_MUSIC_PACKAGE_NAME + "app_widget_name";
    // Do not change these three strings as it will break support with other apps (e.g. last.fm
    // scrobbling)
    public static final String META_CHANGED = RETRO_MUSIC_PACKAGE_NAME + ".metachanged";
    public static final String QUEUE_CHANGED = RETRO_MUSIC_PACKAGE_NAME + ".queuechanged";
    public static final String PLAY_STATE_CHANGED = RETRO_MUSIC_PACKAGE_NAME + ".playstatechanged";
    public static final String FAVORITE_STATE_CHANGED =
            RETRO_MUSIC_PACKAGE_NAME + "favoritestatechanged";
    public static final String REPEAT_MODE_CHANGED = RETRO_MUSIC_PACKAGE_NAME + ".repeatmodechanged";
    public static final String SHUFFLE_MODE_CHANGED =
            RETRO_MUSIC_PACKAGE_NAME + ".shufflemodechanged";
    public static final String MEDIA_STORE_CHANGED = RETRO_MUSIC_PACKAGE_NAME + ".mediastorechanged";
    public static final String CYCLE_REPEAT = RETRO_MUSIC_PACKAGE_NAME + ".cyclerepeat";
    public static final String TOGGLE_SHUFFLE = RETRO_MUSIC_PACKAGE_NAME + ".toggleshuffle";
    public static final String TOGGLE_FAVORITE = RETRO_MUSIC_PACKAGE_NAME + ".togglefavorite";
    public static final String SAVED_POSITION = "POSITION";
    public static final String SAVED_POSITION_IN_TRACK = "POSITION_IN_TRACK";
    public static final String SAVED_SHUFFLE_MODE = "SHUFFLE_MODE";
    public static final String SAVED_REPEAT_MODE = "REPEAT_MODE";
    public static final int RELEASE_WAKELOCK = 0;
    public static final int TRACK_ENDED = 1;
    public static final int TRACK_WENT_TO_NEXT = 2;
    public static final int PLAY_SONG = 3;
    public static final int PREPARE_NEXT = 4;
    public static final int SET_POSITION = 5;
    public static final int FOCUS_CHANGE = 6;
    public static final int DUCK = 7;
    public static final int UNDUCK = 8;
    public static final int RESTORE_QUEUES = 9;
    public static final int SHUFFLE_MODE_NONE = 0;
    public static final int SHUFFLE_MODE_SHUFFLE = 1;
    public static final int REPEAT_MODE_NONE = 0;
    public static final int REPEAT_MODE_ALL = 1;
    public static final int REPEAT_MODE_THIS = 2;
    public static final int SAVE_QUEUES = 0;

    public class MusicBinder extends Binder {
        @NonNull
        public MusicService getService() {
            return MusicService.this;
        }
    }

    private static final long MEDIA_SESSION_ACTIONS =
            PlaybackStateCompat.ACTION_PLAY
                    | PlaybackStateCompat.ACTION_PAUSE
                    | PlaybackStateCompat.ACTION_PLAY_PAUSE
                    | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                    | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                    | PlaybackStateCompat.ACTION_STOP
                    | PlaybackStateCompat.ACTION_SEEK_TO;

    private final IBinder musicBind = new MusicBinder();
    public int nextPosition = -1;
    public boolean pendingQuit = false;
    @Nullable
    public Playback playback;
    private PackageValidator mPackageValidator;




    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

    }

    @Override
    public void onTrackWentToNext() {

    }

    @Override
    public void onTrackEnded() {

    }

    @Override
    public void onTrackEndedWithCrossfade() {

    }
}
