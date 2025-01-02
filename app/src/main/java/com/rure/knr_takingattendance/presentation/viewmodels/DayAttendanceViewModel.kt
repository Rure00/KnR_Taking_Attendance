package com.rure.knr_takingattendance.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import com.rure.knr_takingattendance.presentation.state.home.DayAttendance
import com.rure.knr_takingattendance.presentation.state.home.DayMemberAttendance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayAttendanceViewModel @Inject constructor(

): ViewModel() {
    private val _selectedDay = mutableStateOf<LocalDate>(LocalDate.now())
    val selectedDay get() = _selectedDay

    private val _daySummarize = MutableStateFlow(listOf<DayAttendance>())
    val daySummarize get() = _daySummarize.asStateFlow()

    private val _dayMemberAttendances = MutableStateFlow(listOf<DayMemberAttendance>())
    val dayMemberAttendances get() = _dayMemberAttendances.asStateFlow()


    fun changeMemberAttendance(item: DayMemberAttendance, changedState: AttendanceState) {

    }

}