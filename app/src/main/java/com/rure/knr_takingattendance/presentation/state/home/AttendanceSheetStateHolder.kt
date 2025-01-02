package com.rure.knr_takingattendance.presentation.state.home

data class AttendanceSheetStateHolder(
    val showBottomSheet: Boolean,
    val memberAttendance: DayMemberAttendance? = null,
    val selectedState: AttendanceState? = null
) {
    init {
        if(showBottomSheet && (memberAttendance == null || selectedState == null)) {
            throw Exception("AttendanceSheetState State Error")
        } else if(!showBottomSheet && memberAttendance != null && selectedState != null) {
            throw Exception("AttendanceSheetState State Error")
        }
    }
}
