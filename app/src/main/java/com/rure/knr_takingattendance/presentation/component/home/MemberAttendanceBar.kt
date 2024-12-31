package com.rure.knr_takingattendance.presentation.component.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import com.rure.knr_takingattendance.presentation.state.home.DayMemberAttendance
import com.rure.knr_takingattendance.ui.theme.Gray
import com.rure.knr_takingattendance.ui.theme.TossBlue
import com.rure.knr_takingattendance.ui.theme.Typography
import com.rure.knr_takingattendance.ui.theme.White

@Composable
fun MemberAttendanceBar(memberAttendance: DayMemberAttendance) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .background(White)
            .padding(horizontal = 16.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        val (iconId, textColor) = when(memberAttendance.attendanceStatus) {
            AttendanceState.Attend -> R.drawable.attendant_icon to TossBlue
            AttendanceState.NonAttend -> R.drawable.non_attendant_icon to Gray
            AttendanceState.Tardy -> R.drawable.tardy_icon to Gray
            AttendanceState.Absence -> R.drawable.absence_icon to Red
            else -> null
        }!!


        IconButton(onClick = { }) {
            //ImageVector.vectorResource(iconId)
            Image(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            modifier = Modifier.padding(start = 16.dp).weight(1f),
            text = memberAttendance.name,
            style = Typography.bodyMedium,

        )

        Text(
            text = memberAttendance.attendanceStatus.kr,
            style = Typography.bodyLarge,
            color =textColor
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun Preview() {
//    val ma = DayMemberAttendance(
//        name = "홍길동", localDate = LocalDate.now(), attendanceStatus = AttendanceState.Absence
//    )
//    MemberAttendanceBar(ma)
//}