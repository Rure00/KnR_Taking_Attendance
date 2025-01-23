package com.rure.knr_takingattendance.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.domain.usecase.member.GetMemberByIdUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.GetAttendanceHistoryUseCase
import com.rure.knr_takingattendance.presentation.intent.MemberIntent
import com.rure.knr_takingattendance.presentation.state.UiResult
import com.rure.knr_takingattendance.presentation.state.detail.AttendanceHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceHistoryViewModel @Inject constructor(
    private val getAttendanceHistoryUseCase: GetAttendanceHistoryUseCase,
    private val getMemberByIdUseCase: GetMemberByIdUseCase
): ViewModel() {
    private val _attendanceHistory = MutableStateFlow<UiResult<AttendanceHistory>>(UiResult.Init)
    val attendanceHistory get() = _attendanceHistory.asStateFlow()

    fun getHistory(memberId: Int) {
        emit(UiResult.Loading)
        viewModelScope.launch {
            val member = getMemberByIdUseCase.invoke(id = memberId)
            val history = member?.let { getAttendanceHistoryUseCase.invoke(member = it) }

            if(member == null || history == null) {
                emit(UiResult.Fail)
            } else emit(UiResult.Success(history))
        }
    }

    private fun emit(result: UiResult<AttendanceHistory>) {
        _attendanceHistory.value = result
    }

}