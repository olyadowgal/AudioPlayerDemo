package com.example.android.musicplayerdemo.interfaces


// Allow MainActivity to control media playback of MediaPlayHolder
interface PlayerInterface {

    fun loadMedia(resourceId: Int)

    fun release()

    fun isPlaying(): Boolean

    fun play()

    fun reset()

    fun pause()

    fun initializeProgressCallback()

    fun seekTo(position: Int)

}