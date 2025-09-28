package com.rure.knr_takingattendance.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

enum class Position(val abbr: String) {
    Forward("FW"), Midfielder("MF"), Defender("DF"), GoalKeeper("GK")
}

fun getPositionFalseMap() = mapOf(
    Position.Forward to false,
    Position.Midfielder to false,
    Position.Defender to false,
    Position.GoalKeeper to false,
)

@Entity(
    tableName = "members"
)
data class Member(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "birth") val birth: LocalDate,
    @ColumnInfo(name = "position") val position: Map<Position, Boolean>,
    @ColumnInfo(name = "join_date") val joinDate: LocalDate,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
