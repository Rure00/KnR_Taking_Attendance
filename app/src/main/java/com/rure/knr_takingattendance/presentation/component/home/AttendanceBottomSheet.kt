package com.rure.knr_takingattendance.presentation.component.home

import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.data.entities.MemberParticipation
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import com.rure.knr_takingattendance.ui.theme.Gray
import com.rure.knr_takingattendance.ui.theme.TossBlue
import com.rure.knr_takingattendance.ui.theme.Typography
import com.rure.knr_takingattendance.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)     // ModalBottomSheet에서 뜸.
@Composable
fun AttendanceBottomSheet(
    participation: MemberParticipation,
    onChange: (AttendanceState) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        containerColor = White,
        sheetState = sheetState,
        onDismissRequest = { onChange(participation.attendanceStatus) }
    ) {
        View(participation) {
            coroutineScope.launch {
                delay(300L)
                sheetState.hide()

                onChange(it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun View(participation: MemberParticipation, onChange: (AttendanceState) -> Unit) {
    val currentState = remember {
        mutableStateOf<AttendanceState>(participation.attendanceStatus)
    }

    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) {
        Text(
            text = stringResource(R.string.attendance_bottom_sheet_title, participation.member.name),
            style = Typography.titleMedium,
            modifier = Modifier.fillMaxWidth().padding(start = 9.dp, end = 9.dp, top = 9.dp, bottom = 13.dp)
        )

        AttendanceState.entries.forEach { state ->
            if(state != AttendanceState.All) {
                AttendanceButton(state, state == currentState.value) {
                    currentState.value = state
                    onChange(state)
                }
            }
        }
    }
}

@Composable
private fun AttendanceButton(state: AttendanceState, isSelected: Boolean, onStateSelected: (AttendanceState) -> Unit) {
    val (iconId, textColor) = when(state) {
        AttendanceState.Attend -> R.drawable.attendant_icon to TossBlue
        AttendanceState.NonAttend -> R.drawable.non_attendant_icon to Gray
        AttendanceState.Tardy -> R.drawable.tardy_icon to Gray
        AttendanceState.Absence -> R.drawable.absence_icon to Red
        else -> null
    }!!

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onStateSelected(state) }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )

            Text(
                modifier = Modifier.padding(start = 5.dp).weight(1f),
                text = state.kr,
                style = Typography.bodyMedium,
                color = textColor
            )

            if(isSelected) {
                Image(
                    painter = painterResource(R.drawable.check_image),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp).padding()
                )
            }
        }
    }
}

//@Preview(backgroundColor = Color.WHITE.toLong())
//@Composable
//fun AttendanceBottomSheetPreview() {
//    View(
//        member = MemberParticipation("장동철", LocalDate.now(), AttendanceState.Attend)
//    ) {
//
//    }
//}