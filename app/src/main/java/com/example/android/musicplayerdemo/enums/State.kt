package com.example.android.musicplayerdemo.enums

enum class State(val number: Int) {
    INVALID(-1),
    PLAYING(0),
    PAUSED(1),
    RESET(2),
    COMPLETED(3)
}