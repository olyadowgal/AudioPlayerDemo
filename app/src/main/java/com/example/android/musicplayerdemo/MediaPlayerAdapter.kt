package com.example.android.musicplayerdemo

import android.content.Context
import android.media.MediaPlayer
import com.example.android.musicplayerdemo.MediaPlayerAdapter.Companion.PLAYBACK_POSITION_REFRESH_INTERVAL_MS
import com.example.android.musicplayerdemo.enums.State
import com.example.android.musicplayerdemo.interfaces.PlayerInterface
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MediaPlayerAdapter(context: Context) : PlayerInterface {

    companion object {
        const val PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 1000
    }


    private var mMediaPlayer: MediaPlayer? = null
    private var mResourceId: Int = 0
    //private var mPlaybackInfoListener: PlaybackInfoListener? = null
    private var mExecutor: ScheduledExecutorService? = null
    private var mSeekbarPositionUpdateTask: Runnable? = null
    private val mContext = context

    private fun initializeMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer()
            mMediaPlayer!!.setOnCompletionListener(MediaPlayer.OnCompletionListener {
                // TODO : Add a state change call
                stopUpdatingCallbackWithPosition(true)
                //logToUI("MediaPlayer playback completed")
                //if (mPlaybackInfoListener != null) {
                //   mPlaybackInfoListener!!.onStateChanged(State.COMPLETED)
                //    mPlaybackInfoListener!!.onPlaybackCompleted()
                //}
            })
            //logToUI("mMediaPlayer = new MediaPlayer()")
        }
    }

    //fun setPlaybackInfoListener(listener: PlaybackInfoListener) {
    //    this.mPlaybackInfoListener = listener
    //}

    // Reports media playback position to mPlaybackProgressCallback.
    private fun stopUpdatingCallbackWithPosition(resetUIPlaybackPosition: Boolean) {
        if (mExecutor != null) {
            mExecutor!!.shutdownNow()
            mExecutor = null
            mSeekbarPositionUpdateTask = null
            //if (resetUIPlaybackPosition && mPlaybackInfoListener != null) {
            //    mPlaybackInfoListener!!.onPositionChanged(0)
            //}
        }
    }

    /**
     * Syncs the mMediaPlayer position with mPlaybackProgressCallback via recurring task.
     */
    private fun startUpdatingCallbackWithPosition() {
        if (mExecutor == null) {
            mExecutor = Executors.newSingleThreadScheduledExecutor()
        }
        if (mSeekbarPositionUpdateTask == null) {
            mSeekbarPositionUpdateTask = Runnable { updateProgressCallbackTask() }
        }
        mExecutor?.scheduleAtFixedRate(
            mSeekbarPositionUpdateTask,
            0,
            PLAYBACK_POSITION_REFRESH_INTERVAL_MS.toLong(),
            TimeUnit.MILLISECONDS
        )
    }

    private fun updateProgressCallbackTask() {
        if (mMediaPlayer != null && mMediaPlayer!!.isPlaying()) {
            val currentPosition = mMediaPlayer!!.getCurrentPosition()
            //if (mPlaybackInfoListener != null) {
            //   mPlaybackInfoListener!!.onPositionChanged(currentPosition)
            //}
        }
    }

//    private fun logToUI(message: String) {
//        if (mPlaybackInfoListener != null) {
//            mPlaybackInfoListener!!.onLogUpdated(message)
//        }
//    }

    override fun loadMedia(resourceId: Int) {
        mResourceId = resourceId

        initializeMediaPlayer()

        val assetFileDescriptor = mContext.resources.openRawResourceFd(mResourceId)
        try {
            //logToUI("load() {1. setDataSource}")
            mMediaPlayer!!.setDataSource(assetFileDescriptor)
        } catch (e: Exception) {
            //logToUI(e.toString())
        }


        try {
            //logToUI("load() {2. prepare}")
            mMediaPlayer!!.prepare()
        } catch (e: Exception) {
            //logToUI(e.toString())
        }


        initializeProgressCallback()
        //logToUI("initializeProgressCallback()")
    }

    override fun release() {
        if (mMediaPlayer != null) {
            //logToUI("release() and mMediaPlayer = null")
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    override fun isPlaying(): Boolean {
        return if (mMediaPlayer != null) {
            mMediaPlayer!!.isPlaying()
        } else false
    }

    override fun play() {
        if (mMediaPlayer != null && !mMediaPlayer!!.isPlaying()) {
//            logToUI(
//                String.format(
//                    "playbackStart() %s",
//                    mContext.getResources().getResourceEntryName(mResourceId)
//                )
//            )
            mMediaPlayer!!.start()
            //mPlaybackInfoListener?.onStateChanged(State.PLAYING)
            startUpdatingCallbackWithPosition()
        }
    }

    override fun reset() {
        if (mMediaPlayer != null) {
            //logToUI("playbackReset()")
            mMediaPlayer!!.reset()
            loadMedia(mResourceId)
            //mPlaybackInfoListener?.onStateChanged(State.RESET)
            stopUpdatingCallbackWithPosition(true)
        }
    }

    override fun pause() {
        if (mMediaPlayer != null && mMediaPlayer!!.isPlaying()) {
            mMediaPlayer!!.pause()
            //mPlaybackInfoListener?.onStateChanged(State.PAUSED)
            //logToUI("playbackPause()")
        }
    }

    override fun initializeProgressCallback() {
        val duration = mMediaPlayer!!.getDuration()
//        if (mPlaybackInfoListener != null) {
//            mPlaybackInfoListener!!.onDurationChanged(duration)
//            mPlaybackInfoListener!!.onPositionChanged(0)
//            logToUI(
//                String.format(
//                    "firing setPlaybackDuration(%d sec)",
//                    TimeUnit.MILLISECONDS.toSeconds(duration.toLong())
//                )
//            )
//            logToUI("firing setPlaybackPosition(0)")
//        }
    }

    override fun seekTo(position: Int) {
        if (mMediaPlayer != null) {
            //logToUI(String.format("seekTo() %d ms", position))
            mMediaPlayer!!.seekTo(position)
        }
    }
}