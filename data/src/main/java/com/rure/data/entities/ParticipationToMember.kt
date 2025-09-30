package com.rure.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.rure.domain.models.AttendanceState
import java.time.LocalDate

@Entity(
    tableName = "participation_to_member",
    primaryKeys = ["date", "member_id"]
)
data class ParticipationToMember(
    @ColumnInfo("date") val date: LocalDate,
    @ColumnInfo("member_id") val memberId: Int,
    @ColumnInfo("attendance_status") val attendanceStatus: AttendanceState,
)
