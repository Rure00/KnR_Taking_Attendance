package com.rure.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rure.domain.models.MemberDto
import com.rure.domain.usecase.member.DeleteMemberUseCase
import com.rure.domain.usecase.member.GetAllMembersUseCase
import com.rure.domain.usecase.member.GetMemberByIdUseCase
import com.rure.domain.usecase.member.SaveMemberUseCase
import com.rure.domain.usecase.member.SubscribeMemberFlowUseCase
import com.rure.domain.usecase.member.UpdateMemberUseCase
import com.rure.domain.usecase.participation.DeleteMemberParticipationUseCase
import com.rure.presentation.intent.MemberIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.rure.presentation.state.MemberFlowState

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val saveMemberUseCase: SaveMemberUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase,
    private val updateMemberUseCase: UpdateMemberUseCase,
    private val getAllMembersUseCase: GetAllMembersUseCase,
    private val getMemberByIdUseCase: GetMemberByIdUseCase,

    private val subscribeMemberFlowUseCase: SubscribeMemberFlowUseCase,
): ViewModel() {

    private val tag = "MemberViewModel"

    private val _memberList = MutableStateFlow(listOf<MemberDto>())
    val memberList get() = _memberList.asStateFlow()

    init {
        viewModelScope.launch {
            subscribeMemberFlowUseCase.invoke().collectLatest {
                _memberList.value = it.getOrElse { listOf() }
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