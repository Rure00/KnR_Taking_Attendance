package com.rure.presentation.state.home

import com.rure.knr_takingattendance.domain.usecase.models.MemberParticipation

data class AttendanceSheetStateHolder(
    val showBottomSheet: Boolean,
    val participation: MemberParticipation? = null,
    val selectedState: AttendanceState? = null
) {
    init {
        if(showBottomSheet && (participation == null || selectedState == null)) {
            throw Exception("AttendanceSheetState State Error")
        } else if(!showBottomSheet && participation != null && selectedState != null) {
            throw Exception("AttendanceSheetState State Error")
        }
    }
}
