package com.example.android.musicplayerdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ScrollView
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
        const val MEDIA_RES_ID = R.raw.funky_town
    }

    var mPlayerAdapter : PlayerAdapter? = null
    private var mUserIsSeeking = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_play.setOnClickListener {
            mPlayerAdapter?.play()
        }
        button_pause.setOnClickListener {
            mPlayerAdapter?.pause()
        }
        button_reset.setOnClickListener {
            mPlayerAdapter?.reset()
        }
        initializeSeekbar()
        initializePlaybackController()
    }

    override fun onStart() {
        super.onStart()
        mPlayerAdapter!!.loadMedia(MEDIA_RES_ID)
        Log.d(TAG, "onStart: create MediaPlayer")
    }

    override fun onStop() {
        super.onStop()
        if (mPlayerAdapter != null) {
            if (isChangingConfigurations && mPlayerAdapter!!.isPlaying()) {
                Log.d(TAG, "onStop: don't release MediaPlayer as screen is rotating & playing")
            } else {
                mPlayerAdapter!!.release()
                Log.d(TAG, "onStop: release MediaPlayer")
            }
        }
    }

    //region Initializing elements
    private fun initializePlaybackController() {
        val mMediaPlayerHolder = MediaPlayerHolder(this)
        Log.d(TAG, "initializePlaybackController: created MediaPlayerHolder")
        mMediaPlayerHolder.setPlaybackInfoListener(PlaybackListener())
        mPlayerAdapter = mMediaPlayerHolder
        Log.d(TAG, "initializePlaybackController: MediaPlayerHolder progress callback set")
    }

    private fun initializeSeekbar() {
        seekbar_audio.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                var userSelectedPosition = 0

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    mUserIsSeeking = true
                }

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        userSelectedPosition = progress
                    }
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    mUserIsSeeking = false
                    mPlayerAdapter?.seekTo(userSelectedPosition)
                }
            })
    }
    //endregion


    //region PlaybackListener

    inner class PlaybackListener : PlaybackInfoListener {

        override fun onDurationChanged(duration: Int) {
            seekbar_audio.max = duration
            Log.d(TAG, String.format("setPlaybackDuration: setMax(%d)", duration))
        }

        override fun onPositionChanged(position: Int) {
            if (!mUserIsSeeking) {
                seekbar_audio.setProgress(position, true)
                Log.d(TAG, String.format("setPlaybackPosition: setProgress(%d)", position))
            }
        }

        override fun onStateChanged(state: State) {
            onLogUpdated(String.format("onStateChanged(%s)", state))
        }

        override fun onPlaybackCompleted() {}

        override fun onLogUpdated(formattedMessage: String) {
            if (text_debug != null) {
                text_debug.append(formattedMessage)
                text_debug.append("\n")
                // Moves the scrollContainer focus to the end.
                scroll_container.post { scroll_container.fullScroll(ScrollView.FOCUS_DOWN) }
            }
        }
    }
    //endregion
}
