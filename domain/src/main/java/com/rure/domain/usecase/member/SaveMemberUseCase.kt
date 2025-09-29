package com.rure.domain.usecase.member

import android.util.Log
import com.rure.knr_takingattendance.domain.usecase.models.MemberParticipation
import com.rure.knr_takingattendance.data.entities.Position
import com.rure.knr_takingattendance.domain.repository.MemberRepository
import com.rure.knr_takingattendance.domain.usecase.activity_date.GetActivitiesFromUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.SaveMemberParticipationUseCase
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class SaveMemberUseCase @Inject constructor(
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

        if(newMember == null) {
            Log.e(tag, "Try to Creating New Member but fail...")
            return@withContext
        }

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