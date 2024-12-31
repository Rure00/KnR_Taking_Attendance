package com.rure.knr_takingattendance.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rure.knr_takingattendance.presentation.component.AttendantRadioGroup
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import com.rure.knr_takingattendance.presentation.state.home.DayAttendance
import com.rure.knr_takingattendance.ui.theme.Typography
import java.time.LocalDate

val testMap = mapOf(
    AttendanceState.All to 30,
    AttendanceState.Attend to 20,
    AttendanceState.NonAttend to 7,
    AttendanceState.Absence to 1,
    AttendanceState.Tardy to 2
)

@Composable
fun HomeScreen(toPersonal: () -> Unit) {
    val selectedDay = remember {
        mutableStateOf(LocalDate.now())
    }
    val dayAttendance: State<DayAttendance> = remember {
        derivedStateOf{
            DayAttendance(selectedDay.value, testMap)
        }
    }

    val selectedAttendanceStatus: MutableState<AttendanceState> = remember {
        mutableStateOf(AttendanceState.All)
    }

    Column {
        Column(
            modifier = Modifier
                .padding(vertical = 9.dp)
                .background(Color.White)
                .fillMaxWidth().wrapContentHeight()
                .padding(10.dp)
                ,
        ) {
            Text(
                text = "2024.12.31",
                textAlign = TextAlign.Start,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Black,
                style = Typography.bodyMedium,
            )

            Spacer(modifier = Modifier.padding(5.dp))

            AttendantRadioGroup(selectedAttendanceStatus.value, dayAttendance.value.attendance) {
                selectedAttendanceStatus.value = it
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    HomeScreen {

    }
}