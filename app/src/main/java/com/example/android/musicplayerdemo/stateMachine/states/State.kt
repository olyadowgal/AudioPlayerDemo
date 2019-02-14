package com.example.android.musicplayerdemo.stateMachine

abstract class State(protected val context: PlayerContext) {

    abstract fun handleAction(action: Action) : State
}