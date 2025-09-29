package com.rure.presentation.state.home

enum class AttendanceState(val kr: String) {
    All("전체"),
    Attend("참석"),
    NonAttend("불참"),
    Tardy("지각"),
    Absence("무단"),
}