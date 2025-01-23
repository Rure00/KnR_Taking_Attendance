package com.rure.knr_takingattendance.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.domain.result.MemberFlowResult
import com.rure.knr_takingattendance.domain.usecase.member.DeleteMemberUseCase
import com.rure.knr_takingattendance.domain.usecase.member.GetAllMembersUseCase
import com.rure.knr_takingattendance.domain.usecase.member.GetMemberByIdUseCase
import com.rure.knr_takingattendance.domain.usecase.member.SaveMemberUseCase
import com.rure.knr_takingattendance.domain.usecase.member.SubscribeMemberFlowUseCase
import com.rure.knr_takingattendance.domain.usecase.member.UpdateMemberUseCase
import com.rure.knr_takingattendance.presentation.intent.MemberIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val saveMemberUseCase: SaveMemberUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase,
    private val updateMemberUseCase: UpdateMemberUseCase,
    private val getAllMembersUseCase: GetAllMembersUseCase,
    private val getMemberByIdUseCase: GetMemberByIdUseCase,

    private val subscribeMemberFlowUseCase: SubscribeMemberFlowUseCase
): ViewModel() {

    private val tag = "MemberViewModel"

    private val _memberList = MutableStateFlow(listOf<Member>())
    val memberList get() = _memberList.asStateFlow()

    init {
        viewModelScope.launch {
            subscribeMemberFlowUseCase.invoke().collectLatest {
                when(it) {
                    is MemberFlowResult.Loading -> { }
                    is MemberFlowResult.Success ->{
                        _memberList.value = it.list
                    }
                    is MemberFlowResult.Fail -> {
                        Log.e(tag, "Collect MemberFlow Failed: ${it.exception.message}")
                    }
                }

            }
        }
    }

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
                    _memberList.value.firstOrNull { it.id == intent.id }
                }
            }
            is MemberIntent.LoadAllMembers -> {
                viewModelScope.launch {
                    _memberList.value = getAllMembersUseCase.invoke()
                }
            }
        }
    }

    fun getMemberById(id: Int) = memberList.value.firstOrNull { it.id == id }
}