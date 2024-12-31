package com.rure.knr_takingattendance.presentation.navigation

sealed class Destination(
    val label: String,
    val route: String,
    val showOption: Boolean,
    val showBackBtn: Boolean,
) {
    data object Home: Destination(
        "K&R 출석", "home", true, false
    )

    data object Option: Destination(
        "설정", "option",false, true
    )

    data object PersonalAttend: Destination(
        "개인 출석", "personal",false, true
    )

    data object AddMember: Destination(
        "팀원 추가", "addMember",false, true
    )
}