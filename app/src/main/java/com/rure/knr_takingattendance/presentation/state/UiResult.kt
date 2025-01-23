package com.rure.knr_takingattendance.presentation.state

sealed class UiResult<out T> {
    data class Success<out T>(val value: T): UiResult<T>()
    data object Fail: UiResult<Nothing>()
    data object Loading: UiResult<Nothing>()
    data object Init: UiResult<Nothing>()
}