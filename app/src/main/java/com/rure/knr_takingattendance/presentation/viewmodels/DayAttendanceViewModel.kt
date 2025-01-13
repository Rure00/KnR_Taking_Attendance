package com.rure.knr_takingattendance.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import com.rure.knr_takingattendance.domain.result.MemberFlowResult
import com.rure.knr_takingattendance.domain.usecase.member.GetAllMembersUseCase
import com.rure.knr_takingattendance.domain.usecase.member.SubscribeMemberFlowUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.DeleteMemberParticipationUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.GetParticipationByMemberUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.GetParticipationWhenUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.SaveMemberParticipationUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.UpdateMemberParticipationUseCase
import com.rure.knr_takingattendance.presentation.intent.ParticipationIntent
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import com.rure.knr_takingattendance.presentation.state.home.DayAttendanceSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayAttendanceViewModel @Inject constructor(
    private val saveMemberParticipationUseCase: SaveMemberParticipationUseCase,
    private val deleteMemberParticipationUseCase: DeleteMemberParticipationUseCase,
    private val updateMemberParticipationUseCase: UpdateMemberParticipationUseCase,
    private val getParticipationWhenUseCase: GetParticipationWhenUseCase,
    private val getParticipationByMemberUseCase: GetParticipationByMemberUseCase,

    private val getAllMembersUseCase: GetAllMembersUseCase,
    private val subscribeMemberFlowUseCase: SubscribeMemberFlowUseCase
): ViewModel() {

    private val _memberList = MutableStateFlow(listOf<Member>())
    val memberList get() = _memberList.asStateFlow()

    private val _selectedDay = mutableStateOf<LocalDate>(LocalDate.now())
    val selectedDay get() = _selectedDay

    private val _daySummarize = MutableStateFlow(
        DayAttendanceSummary(selectedDay.value, 0, 0, 0, 0, 0)
    )
    val daySummarize get() = _daySummarize.asStateFlow()

    private val _memberParticipation = MutableStateFlow(listOf<MemberParticipation>())
    val memberParticipation get() = _memberParticipation.asStateFlow()


    init {
        viewModelScope.launch {
            subscribeMemberFlowUseCase.invoke().collectLatest {
                when(it) {
                    is MemberFlowResult.Loading -> { }
                    is MemberFlowResult.Success ->{
                        _memberList.value = it.list
                        emit(ParticipationIntent.InitParticipation)
                    }
                    is MemberFlowResult.Fail -> {
                        Log.e("DayAttendanceViewModel", "Collect MemberFlow Failed: ${it.exception.message}")
                    }
                }

            }
        }
    }


    fun emit(intent: ParticipationIntent) {
        when(intent) {
            is ParticipationIntent.InitParticipation -> {
                viewModelScope.launch {
                    emit(ParticipationIntent.GetParticipationWhen(selectedDay.value))
                }
            }

            is ParticipationIntent.SaveParticipation -> {
                viewModelScope.launch {
                    saveMemberParticipationUseCase.invoke(
                        MemberParticipation(
                            intent.date, intent.member.id, intent.attendanceState, intent.member
                        )
                    )
                }
            }
            is ParticipationIntent.DeleteParticipation -> {
                viewModelScope.launch {

                }
            }
            is ParticipationIntent.UpdateParticipation -> {
                viewModelScope.launch {

                }
            }
            is ParticipationIntent.GetParticipationWhen -> {
                viewModelScope.launch {
                    val membersAttendance = getParticipationWhenUseCase.invoke(intent.date).toMutableList()
                    memberList.value.forEach { member ->
                        if(!membersAttendance.any { it.memberId == member.id }) {
                            membersAttendance.add(
                                MemberParticipation(
                                    date = selectedDay.value,
                                    memberId = member.id,
                                    attendanceStatus = AttendanceState.NonAttend,
                                    member = member
                                )
                            )
                        }
                    }
                    _memberParticipation.value = membersAttendance

                    val attendanceToList = _memberParticipation.value.groupBy {
                        it.attendanceStatus
                    }
                    _daySummarize.value = DayAttendanceSummary(
                        date = selectedDay.value,
                        allNum = _memberParticipation.value.size,
                        attendNum = attendanceToList[AttendanceState.Attend]?.size ?: 0,
                        nonAttendNum = attendanceToList[AttendanceState.NonAttend]?.size ?: 0,
                        tardyNum = attendanceToList[AttendanceState.Tardy]?.size ?: 0,
                        absenceNum = attendanceToList[AttendanceState.Absence]?.size ?: 0
                    )
                }
            }
            is ParticipationIntent.GetParticipationByMember -> {
                viewModelScope.launch {

                }
            }
        }
    }

    fun changeSelectedDay(date: LocalDate) {
        _selectedDay.value = date
        emit(ParticipationIntent.GetParticipationWhen(date))
    }




    private fun getSummaryFromDayParticipation(date: LocalDate, list: List<MemberParticipation>): DayAttendanceSummary {
        val allNum = list.size
        val numArr = Array(5) {0}

        list.forEach {
            numArr[it.attendanceStatus.ordinal] = numArr[it.attendanceStatus.ordinal] + 1
        }

        return DayAttendanceSummary(
            date = date,
            allNum = allNum,
            attendNum = numArr[1],
            nonAttendNum = numArr[2],
            tardyNum = numArr[3],
            absenceNum = numArr[4]
        )
    }
}