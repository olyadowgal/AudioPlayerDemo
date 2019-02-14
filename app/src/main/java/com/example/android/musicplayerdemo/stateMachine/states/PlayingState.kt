package com.example.android.musicplayerdemo.stateMachine

import com.example.android.musicplayerdemo.MediaPlayerAdapter
import com.example.android.musicplayerdemo.stateMachine.Action.*

class PlayingState(context: PlayerContext) : State(context) {

    override fun handleAction(action: Action) : State {
        when (action) {
            is Play -> {
                context.musicPlayer = MediaPlayerAdapter(context.context)
                context.musicPlayer.loadMedia(context.playlist[context.currSong])

            }
            is Pause -> {
                context.musicPlayer.pause()
            }
            is Stop -> {
                context.musicPlayer.reset()
            }
        }
        return context.currState
    }


}