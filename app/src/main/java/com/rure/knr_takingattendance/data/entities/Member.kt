package com.rure.knr_takingattendance.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

enum class Position(val abbr: String) {
    Forward("FW"), Midfielder("MF"), Defender("DF"), GoalKeeper("GK")
}

@Entity(
    tableName = "members"
)
data class Member(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "position") val position: Position,
    @ColumnInfo(name = "join_date") val joinDate: LocalDate,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
