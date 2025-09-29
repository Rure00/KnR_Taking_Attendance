package com.rure.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rure.knr_takingattendance.data.entities.ActivityDate
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.domain.usecase.models.MemberParticipation
import com.rure.knr_takingattendance.domain.result.MemberFlowResult
import com.rure.knr_takingattendance.domain.usecase.activity_date.DeleteActivityUseCase
import com.rure.knr_takingattendance.domain.usecase.activity_date.SaveActivityDateUseCase
import com.rure.knr_takingattendance.domain.usecase.member.SubscribeMemberFlowUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.DeleteMemberParticipationUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.GetParticipationByMemberUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.GetParticipationWhenUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.InitDayAttendanceUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.SaveMemberParticipationUseCase
import com.rure.knr_takingattendance.presentation.intent.ParticipationIntent
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import com.rure.knr_takingattendance.presentation.state.home.DayAttendanceSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _memberList = MutableStateFlow(listOf<Member>())

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
                        emit(ParticipationIntent.GetParticipationWhen(selectedDay.value))
                    }
                    is MemberFlowResult.Fail -> {
                        Log.e(tag, "Collect MemberFlow Failed: ${it.exception.message}")
                    }
                }

            }
        }
    }


    fun emit(intent: ParticipationIntent) {
        when(intent) {
            is ParticipationIntent.CreateActivityDate -> {
                viewModelScope.launch {
                    saveActivityDateUseCase.invoke(ActivityDate(selectedDay.value))
                    _memberParticipation.value = initDayAttendanceUseCase.invoke(selectedDay.value)
                }
            }
            is ParticipationIntent.DeleteActivityDate -> {
                viewModelScope.launch {
                    deleteActivityUseCase.invoke(ActivityDate(selectedDay.value))
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