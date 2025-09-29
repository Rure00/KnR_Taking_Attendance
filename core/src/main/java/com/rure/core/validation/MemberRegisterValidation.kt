package com.rure.core.validation

import java.time.LocalDate

object MemberRegisterValidation {
    fun checkName(name: String): Boolean {
        return if(name.isEmpty()) {
            false
        } else if(!Regex("^[가-힣]*\$").matches(name) || name.contains(" ")) {
            false
        } else if(name.length < 2) false
        else true
    }
    fun checkPhoneNumber(number: String): Boolean {
        return (number.length == 11)
    }
}