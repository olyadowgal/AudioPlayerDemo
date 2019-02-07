package com.example.android.musicplayerdemo.interfaces

import com.example.android.musicplayerdemo.enums.State

interface PlaybackInfoListener {


    fun onLogUpdated(formattedMessage: String) {}

    fun onDurationChanged(duration: Int) {}

    fun onPositionChanged(position: Int) {}

    fun onStateChanged(state: State) {}

    fun onPlaybackCompleted() {}
}