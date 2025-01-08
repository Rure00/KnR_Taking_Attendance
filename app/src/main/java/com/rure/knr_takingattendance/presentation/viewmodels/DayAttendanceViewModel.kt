package com.rure.knr_takingattendance.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import com.rure.knr_takingattendance.domain.usecase.participation.DeleteMemberParticipationUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.GetParticipationByMemberUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.GetParticipationWhenUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.SaveMemberParticipationUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.UpdateMemberParticipationUseCase
import com.rure.knr_takingattendance.presentation.intent.ParticipationIntent
import com.rure.knr_takingattendance.presentation.state.home.DayAttendanceSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val getParticipationByMemberUseCase: GetParticipationByMemberUseCase
): ViewModel() {
    private val _selectedDay = mutableStateOf<LocalDate>(LocalDate.now())
    val selectedDay get() = _selectedDay

    private val _daySummarize = MutableStateFlow(
        DayAttendanceSummary(selectedDay.value, 0, 0, 0, 0, 0)
    )
    val daySummarize get() = _daySummarize.asStateFlow()

    private val _memberParticipation = MutableStateFlow(listOf<MemberParticipation>())
    val memberParticipation get() = _memberParticipation.asStateFlow()

    fun emit(intent: ParticipationIntent) {
        when(intent) {
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

        viewModelScope.launch {
            _daySummarize.value = if(selectedDay.value.dayOfWeek == DayOfWeek.SUNDAY) {
                getSummaryFromDayParticipation(date, getParticipationWhenUseCase.invoke(date))
            } else DayAttendanceSummary(selectedDay.value, 0, 0, 0, 0, 0)
        }
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