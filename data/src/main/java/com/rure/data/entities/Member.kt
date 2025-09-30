package com.rure.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rure.domain.models.MemberDto
import com.rure.domain.models.Position
import java.time.LocalDate

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

fun Member.toDto(): MemberDto {
    return MemberDto(
        name = this.name,
        birth = this.birth,
        position = this.position,
        joinDate = this.joinDate,
        phoneNumber = this.phoneNumber,
        id = this.id
    )
}
fun MemberDto.toEntity(): Member {
    return Member(
        id = id,
        name = name,
        birth = birth,
        position = position,
        joinDate = joinDate,
        phoneNumber = phoneNumber
    )
}
