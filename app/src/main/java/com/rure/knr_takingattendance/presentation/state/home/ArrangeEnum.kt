package com.rure.knr_takingattendance.presentation.state.home

sealed class ArrangeEnum(val data: String) {
    data object Name: ArrangeEnum("이름")
    data object AttendanceRate: ArrangeEnum("출석률")
}