package com.rure.presentation.intent

import com.rure.domain.models.MemberDto
import com.rure.domain.models.Position
import java.time.LocalDate

sealed class MemberIntent {
    data class SaveMember(
        val name: String,
        val birth: LocalDate,
        val position: Map<Position, Boolean>,
        val joinDate: LocalDate,
        val phoneNumber: String
    ): MemberIntent()

    data class DeleteMember(val member: MemberDto): MemberIntent()

    data class UpdateMember(val member: MemberDto): MemberIntent()

    data object LoadAllMembers: MemberIntent()

    data class GetMemberById(val id: Int): MemberIntent()

}