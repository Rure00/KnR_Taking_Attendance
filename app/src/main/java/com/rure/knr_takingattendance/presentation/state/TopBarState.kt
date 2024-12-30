package com.rure.knr_takingattendance.presentation.state

data class TopBarState(
    val title: String,
    val showBackButton: Boolean,
    val onBackButton: (() -> Unit)? = null
)
