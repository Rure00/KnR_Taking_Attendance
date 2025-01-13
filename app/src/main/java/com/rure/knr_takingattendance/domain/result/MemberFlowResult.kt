package com.rure.knr_takingattendance.domain.result

import com.rure.knr_takingattendance.data.entities.Member
import java.lang.Exception

sealed class MemberFlowResult {
    data object Loading: MemberFlowResult()
    data class Success(val list: List<Member>): MemberFlowResult()
    data class Fail(val exception: Throwable): MemberFlowResult()
}