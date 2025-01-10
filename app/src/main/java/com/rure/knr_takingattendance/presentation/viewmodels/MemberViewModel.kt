package com.rure.knr_takingattendance.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rure.knr_takingattendance.domain.usecase.member.DeleteMemberUseCase
import com.rure.knr_takingattendance.domain.usecase.member.GetAllMembersUseCase
import com.rure.knr_takingattendance.domain.usecase.member.GetMemberByIdUseCase
import com.rure.knr_takingattendance.domain.usecase.member.SaveMemberUseCase
import com.rure.knr_takingattendance.domain.usecase.member.UpdateMemberUseCase
import com.rure.knr_takingattendance.presentation.intent.MemberIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val saveMemberUseCase: SaveMemberUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase,
    private val updateMemberUseCase: UpdateMemberUseCase,
    private val getAllMembersUseCase: GetAllMembersUseCase,
    private val getMemberByIdUseCase: GetMemberByIdUseCase,
): ViewModel() {

    fun emit(intent: MemberIntent) {
        when(intent) {
            is MemberIntent.SaveMember -> {
                viewModelScope.launch {
                    saveMemberUseCase.invoke(
                        intent.name,
                        intent.birth,
                        intent.position,
                        intent.joinDate,
                        intent.phoneNumber
                    )
                }
            }
            is MemberIntent.DeleteMember -> {
                viewModelScope.launch {
                    deleteMemberUseCase.invoke(intent.member)
                }
            }
            is MemberIntent.UpdateMember -> {
                viewModelScope.launch {
                    updateMemberUseCase.invoke(intent.member)
                }
            }
            is MemberIntent.GetMemberById -> {
                viewModelScope.launch {
                    //TODO

                }
            }
            is MemberIntent.LoadAllMembers -> {
                viewModelScope.launch {
                    //TODO

                }
            }
        }
    }



}