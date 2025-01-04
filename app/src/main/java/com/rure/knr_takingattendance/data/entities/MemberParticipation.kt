package com.rure.knr_takingattendance.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import java.time.LocalDate

@Entity(
    tableName = "member_participation",
    primaryKeys = ["firstName", "lastName"]
)
data class MemberParticipation(
    @ColumnInfo("date") val date: LocalDate,
    @ColumnInfo("member") val memberId: Int,
    @ColumnInfo("attendance_status") val attendanceStatus: AttendanceState,
    @Ignore val picture: Member? = null
)
