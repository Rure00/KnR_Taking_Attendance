package com.rure.knr_takingattendance.presentation.navigation

sealed class Destination(
    val label: String,
    val showOption: Boolean,
    val showBackBtn: Boolean,
) {
    data object Home: Destination(
        "K&R 출석", true, false
    )

    data object Option: Destination(
        "설정", false, true
    )

    data object PersonalAttend: Destination(
        "개인 출석", false, true
    )

    data object AddMember: Destination(
        "팀원 추가", false, true
    )
}