package com.rure.domain.models

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

data class MemberDto(
    val name: String,
    val birth: LocalDate,
    val position: Map<Position, Boolean>,
    val joinDate: LocalDate,
    val phoneNumber: String,
    val id: Int = 0,
)
