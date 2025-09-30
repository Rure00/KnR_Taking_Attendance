package com.rure.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rure.domain.models.ActivityDateDto
import com.rure.domain.models.DayAttendanceSummaryDto
import com.rure.domain.models.AttendanceState
import com.rure.domain.models.MemberDto
import com.rure.domain.models.MemberParticipation
import com.rure.domain.usecase.activity_date.DeleteActivityUseCase
import com.rure.domain.usecase.activity_date.SaveActivityDateUseCase
import com.rure.domain.usecase.member.SubscribeMemberFlowUseCase
import com.rure.domain.usecase.participation.DeleteMemberParticipationUseCase
import com.rure.domain.usecase.participation.GetParticipationByMemberUseCase
import com.rure.domain.usecase.participation.GetParticipationWhenUseCase
import com.rure.domain.usecase.participation.InitDayAttendanceUseCase
import com.rure.domain.usecase.participation.SaveMemberParticipationUseCase
import com.rure.presentation.intent.ParticipationIntent
import com.rure.presentation.state.MemberFlowState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayAttendanceViewModel @Inject constructor(
    private val saveMemberParticipationUseCase: SaveMemberParticipationUseCase,
    private val deleteMemberParticipationUseCase: DeleteMemberParticipationUseCase,
    private val getParticipationWhenUseCase: GetParticipationWhenUseCase,
    private val getParticipationByMemberUseCase: GetParticipationByMemberUseCase,

    private val subscribeMemberFlowUseCase: SubscribeMemberFlowUseCase,
    private val initDayAttendanceUseCase: InitDayAttendanceUseCase,

    private val saveActivityDateUseCase: SaveActivityDateUseCase,
    private val deleteActivityUseCase: DeleteActivityUseCase
): ViewModel() {
    private val tag = "DayAttendanceViewModel"

    private val _memberList = MutableStateFlow(listOf<MemberDto>())

    private val _selectedDay = mutableStateOf<LocalDate>(LocalDate.now())
    val selectedDay get() = _selectedDay

    private val _daySummarize = MutableStateFlow(
        DayAttendanceSummaryDto(selectedDay.value, 0, 0, 0, 0, 0)
    )
    val daySummarize get() = _daySummarize.asStateFlow()

    private val _memberParticipation = MutableStateFlow(listOf<MemberParticipation>())
    val memberParticipation get() = _memberParticipation.asStateFlow()

    init {
        viewModelScope.launch {
            subscribeMemberFlowUseCase.invoke()
                .onStart {

                }
                .collect { result ->
                    _memberList.value = result.getOrElse { listOf() }
                }
        }
    }


    fun emit(intent: ParticipationIntent) {
        when(intent) {
            is ParticipationIntent.CreateActivityDate -> {
                viewModelScope.launch {
                    saveActivityDateUseCase.invoke(ActivityDateDto(selectedDay.value))
                    _memberParticipation.value = initDayAttendanceUseCase.invoke(selectedDay.value)
                }
            }
            is ParticipationIntent.DeleteActivityDate -> {
                viewModelScope.launch {
                    deleteActivityUseCase.invoke(ActivityDateDto(selectedDay.value))
                    _memberParticipation.value = listOf()
                }
            }

            is ParticipationIntent.SaveParticipation -> {
                viewModelScope.launch {
                    _memberParticipation.value = _memberParticipation.value.map {
                        if(it.memberId == intent.dayMemberAttendance.memberId) intent.dayMemberAttendance
                        else it
                    }

                    updateDaySummarize()
                    saveMemberParticipationUseCase.invoke(intent.dayMemberAttendance)
                }
            }
            is ParticipationIntent.DeleteParticipation -> {
                viewModelScope.launch {
                    _memberParticipation.value = _memberParticipation.value.filter {
                        it.memberId != intent.dayMemberAttendance.memberId
                    }

                    deleteMemberParticipationUseCase.invoke(intent.dayMemberAttendance)
                }
            }
            is ParticipationIntent.GetParticipationWhen -> {
                viewModelScope.launch {
                    val membersAttendance = getParticipationWhenUseCase.invoke(intent.date).toMutableList()
                    _memberParticipation.value = membersAttendance

                    updateDaySummarize()
                }
            }
        }
    }

    fun changeSelectedDay(date: LocalDate) {
        _selectedDay.value = date
        emit(ParticipationIntent.GetParticipationWhen(date))
    }

    private fun updateDaySummarize() {
        val attendanceToList = _memberParticipation.value.groupBy {
            it.attendanceStatus
        }
        _daySummarize.value = DayAttendanceSummaryDto(
            date = selectedDay.value,
            allNum = _memberParticipation.value.size,
            attendNum = attendanceToList[AttendanceState.Attend]?.size ?: 0,
            nonAttendNum = attendanceToList[AttendanceState.NonAttend]?.size ?: 0,
            tardyNum = attendanceToList[AttendanceState.Tardy]?.size ?: 0,
            absenceNum = attendanceToList[AttendanceState.Absence]?.size ?: 0
        )
    }
}