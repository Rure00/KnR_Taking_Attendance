package com.rure.knr_takingattendance.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.rure.knr_takingattendance.presentation.component.home.AttendantRadioGroup
import com.rure.knr_takingattendance.presentation.component.home.MemberAttendanceBar
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import com.rure.knr_takingattendance.presentation.state.home.DayAttendance
import com.rure.knr_takingattendance.presentation.state.home.DayMemberAttendance
import com.rure.knr_takingattendance.ui.theme.Gray
import com.rure.knr_takingattendance.ui.theme.Typography
import java.time.LocalDate

val testMap = mapOf(
    AttendanceState.All to 30,
    AttendanceState.Attend to 20,
    AttendanceState.NonAttend to 7,
    AttendanceState.Absence to 1,
    AttendanceState.Tardy to 2
)
val testMemberAttendance = listOf(
    DayMemberAttendance("김민수", LocalDate.now(), AttendanceState.Attend),
    DayMemberAttendance("김진수", LocalDate.now(), AttendanceState.Attend),
    DayMemberAttendance("노홍철", LocalDate.now(), AttendanceState.NonAttend),
    DayMemberAttendance("두정민", LocalDate.now(), AttendanceState.Tardy),
    DayMemberAttendance("배진우", LocalDate.now(), AttendanceState.Attend),
    DayMemberAttendance("성승모", LocalDate.now(), AttendanceState.Attend),
    DayMemberAttendance("이폐급", LocalDate.now(), AttendanceState.Absence),
    DayMemberAttendance("황유상", LocalDate.now(), AttendanceState.Attend),
    DayMemberAttendance("김민수", LocalDate.now(), AttendanceState.Attend),
    DayMemberAttendance("김진수", LocalDate.now(), AttendanceState.Attend),
    DayMemberAttendance("노홍철", LocalDate.now(), AttendanceState.NonAttend),
    DayMemberAttendance("두정민", LocalDate.now(), AttendanceState.Attend),
    DayMemberAttendance("배진우", LocalDate.now(), AttendanceState.Attend),
    DayMemberAttendance("성승모", LocalDate.now(), AttendanceState.Attend),
    DayMemberAttendance("이폐급", LocalDate.now(), AttendanceState.Absence),
    DayMemberAttendance("황유상", LocalDate.now(), AttendanceState.Attend),
)

@Composable
fun HomeScreen(toPersonal: () -> Unit) {
    val selectedDay = remember {
        mutableStateOf(LocalDate.now())
    }
    val dayAttendance: State<DayAttendance> = remember {
        derivedStateOf{
            //TODO: TestCode 고치기
            DayAttendance(selectedDay.value, testMap)
        }
    }
    val selectedAttendanceStatus: MutableState<AttendanceState> = remember {
        mutableStateOf(AttendanceState.All)
    }

    val dayMemberAttendances = remember {
        //TODO: TestCode 고치기
        //mutableStateOf(listOf<DayMemberAttendance>())
        mutableStateOf(testMemberAttendance)
    }

    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .padding(vertical = 9.dp)
                    .background(Color.White)
                    .fillMaxWidth().wrapContentHeight()
                    .padding(10.dp),
            ) {
                Text(
                    text = selectedDay.value.toString().replace("-", "."),
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

            Row(modifier = Modifier.padding(start = 9.dp, bottom = 9.dp)) {
                Text(
                    text = "정렬:",
                    style = Typography.labelMedium,
                    color = Gray
                )
                Text(
                    text = "이름",
                    style = Typography.labelMedium,
                    color = Gray
                )
            }
        }

        items(dayMemberAttendances.value) {
            MemberAttendanceBar(it)
            Spacer(modifier = Modifier.height(3.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    HomeScreen {

    }
}