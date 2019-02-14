package com.example.android.musicplayerdemo.stateMachine.states

import com.example.android.musicplayerdemo.stateMachine.Action
import com.example.android.musicplayerdemo.stateMachine.PlayerContext

class IdleState(context: PlayerContext) : State(context) {

    override fun handleAction(action: Action): State = when(action) {
        is Action.Play -> {
            val assetFileDescriptor = context.context.resources.openRawResourceFd(context.playlist[0])
            try {
                context.mediaPlayer.setDataSource(assetFileDescriptor)
                context.mediaPlayer.prepare()
            } catch (e: Exception) {
            }
            context.mediaPlayer.start()
            PlayingState(context,0)
        }
        else -> this
    }
}