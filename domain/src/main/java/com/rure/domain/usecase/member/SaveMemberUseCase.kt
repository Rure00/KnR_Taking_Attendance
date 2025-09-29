package com.rure.domain.usecase.member

import com.rure.domain.models.MemberParticipation
import com.rure.domain.entities.Position
import com.rure.domain.repository.MemberRepository
import com.rure.domain.usecase.activity_date.GetActivitiesFromUseCase
import com.rure.domain.usecase.participation.SaveMemberParticipationUseCase
import com.rure.domain.models.AttendanceState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate

class SaveMemberUseCase(
    private val memberRepository: MemberRepository,
    private val saveMemberParticipationUseCase: SaveMemberParticipationUseCase,
    private val getActivitiesFromUseCase: GetActivitiesFromUseCase,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val tag = "SaveMemberUseCase"
    suspend operator fun invoke(
        name: String,
        birth: LocalDate,
        position: Map<Position, Boolean>,
        joinDate: LocalDate,
        phoneNumber: String,
    ) = withContext(ioDispatcher) {
        val newMember = memberRepository.insertMember(name, birth, position, joinDate, phoneNumber)
            ?: return@withContext

        getActivitiesFromUseCase.invoke(joinDate).forEach {
            saveMemberParticipationUseCase(
                participation = MemberParticipation(
                    date = it.date,
                    memberId = newMember.id,
                    attendanceStatus = AttendanceState.NonAttend,
                    member = newMember
                )
            )
        }
    }
}