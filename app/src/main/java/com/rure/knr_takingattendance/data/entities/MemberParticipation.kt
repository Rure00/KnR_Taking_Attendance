package com.rure.knr_takingattendance.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import java.time.LocalDate


data class MemberParticipation(
    val date: LocalDate,
    val memberId: Int,
    val attendanceStatus: AttendanceState,
    var member: Member
) {

}
