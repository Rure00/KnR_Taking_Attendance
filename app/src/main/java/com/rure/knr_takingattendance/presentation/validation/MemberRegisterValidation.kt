package com.rure.knr_takingattendance.presentation.validation

import java.time.LocalDate

object MemberRegisterValidation {
    fun checkName(name: String): Boolean {
        return true
    }
    fun checkPhoneNumber(number: String): Boolean {
        return true
    }
    fun checkJoiningDate(date: LocalDate): Boolean {
        return true
    }
    fun checkBirth(date: LocalDate): Boolean {
        return true
    }
}