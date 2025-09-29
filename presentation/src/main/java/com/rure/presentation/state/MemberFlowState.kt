package com.rure.presentation.state

import com.rure.data.entities.Member

sealed class MemberFlowState {
    data object Loading: MemberFlowState()
    data class Success(val list: List<Member>): MemberFlowState()
    data class Fail(val exception: Throwable): MemberFlowState()
}