package com.example.android.musicplayerdemo.stateMachine

import android.content.Context
import android.media.MediaPlayer
import com.example.android.musicplayerdemo.stateMachine.states.IdleState
import com.example.android.musicplayerdemo.stateMachine.states.State

class PlayerStateMachine(override val context: Context) : PlayerContext {

    override val mediaPlayer: MediaPlayer = MediaPlayer()
    override val playlist: MutableList<Int> = ArrayList()

    var currState: State = IdleState(this)

    fun performAction(action: Action) {
        currState = currState.handleAction(action)
    }

    fun setPlaylist(playlist: MutableList<Int>, action: Action? = Action.Play()) {
        currState.handleAction(Action.Stop())
        this.playlist.clear()
        this.playlist.addAll(playlist)
        action?.let {
            currState.handleAction(action)
        }
    }
}