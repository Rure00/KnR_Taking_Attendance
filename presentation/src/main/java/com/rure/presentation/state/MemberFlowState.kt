package com.rure.presentation.state

import com.rure.domain.models.MemberDto

sealed class MemberFlowState {
    data object Loading: MemberFlowState()
    data class Success(val list: List<MemberDto>): MemberFlowState()
    data class Fail(val exception: Throwable): MemberFlowState()
}