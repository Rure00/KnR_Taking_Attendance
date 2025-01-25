package com.rure.knr_takingattendance.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import java.time.LocalDate

@Entity(
    tableName = "member_participation",
    primaryKeys = ["date", "member_id"]
)
data class ParticipationToMember(
    @ColumnInfo("date") val date: LocalDate,
    @ColumnInfo("member_id") val memberId: Int,
    @ColumnInfo("attendance_status") val attendanceStatus: AttendanceState,
)
