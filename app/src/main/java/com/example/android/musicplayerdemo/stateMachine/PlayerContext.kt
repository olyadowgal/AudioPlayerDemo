package com.example.android.musicplayerdemo.stateMachine

import com.example.android.musicplayerdemo.MediaPlayerAdapter

interface Context {
    var context: Context
    get() = context
    set(value) {context = value}
    var currentState: PlayerState,
    var musicPlayer : MediaPlayerAdapter
}