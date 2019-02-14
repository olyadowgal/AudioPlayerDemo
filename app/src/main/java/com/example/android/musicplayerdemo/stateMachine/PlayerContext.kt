package com.example.android.musicplayerdemo.stateMachine

import android.content.Context
import android.media.MediaPlayer

interface PlayerContext {
    val context: Context
    val mediaPlayer: MediaPlayer
    val playlist: MutableList<Int>
}