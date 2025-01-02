package com.rure.knr_takingattendance.presentation.screen

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.presentation.component.home.AttendanceBottomSheet
import com.rure.knr_takingattendance.presentation.component.home.AttendantRadioGroup
import com.rure.knr_takingattendance.presentation.component.home.HomeDatePickerModal
import com.rure.knr_takingattendance.presentation.component.home.MemberAttendanceBar
import com.rure.knr_takingattendance.presentation.state.home.ArrangeEnum
import com.rure.knr_takingattendance.presentation.state.home.AttendanceSheetStateHolder
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import com.rure.knr_takingattendance.presentation.state.home.DayAttendance
import com.rure.knr_takingattendance.presentation.state.home.DayMemberAttendance
import com.rure.knr_takingattendance.presentation.viewmodels.DayAttendanceViewModel
import com.rure.knr_takingattendance.ui.theme.Gray
import com.rure.knr_takingattendance.ui.theme.TossBlue
import com.rure.knr_takingattendance.ui.theme.Typography
import com.rure.knr_takingattendance.ui.theme.White
import java.time.DayOfWeek
import java.time.LocalDate
import kotlin.math.roundToInt

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
fun HomeScreen(
    toPersonal: () -> Unit,
    dayAttendanceViewModel: DayAttendanceViewModel = hiltViewModel<DayAttendanceViewModel>()
) {
    val selectedAttendanceStatus = remember {
        mutableStateOf(AttendanceState.All)
    }

    val showDatePicker = remember { mutableStateOf(false) }
    val selectedDay = remember {
        dayAttendanceViewModel.selectedDay
    }

    val dayAttendance = remember {
        //TODO: TestCode 고치기
        derivedStateOf {
            DayAttendance(selectedDay.value, testMap)
        }
    }
//    val dayAttendance = remember {
//        derivedStateOf {
//            when(selectedAttendanceStatus.value) {
//                AttendanceState.All -> dayAttendanceViewModel.dayMemberAttendances.value
//                else -> {
//                    dayAttendanceViewModel.dayMemberAttendances.value.filter {
//                        it.attendanceStatus == selectedAttendanceStatus.value
//                    }
//                }
//            }
//        }
//    }
    val dayMemberAttendances = remember {
        //dayAttendanceViewModel.dayMemberAttendances

        //TODO: TestCode 고치기
        mutableStateOf(testMemberAttendance)
    }

    val listState = rememberLazyListState()

    val showArrangeDropDown = remember { mutableStateOf(false) }
    val arrangeEnum = remember {
        mutableStateOf<ArrangeEnum>(ArrangeEnum.Name)
    }

    val bottomSheetStateHolder = remember { mutableStateOf(AttendanceSheetStateHolder(false)) }



    LazyColumn(
        state = listState,
    ) {
        item {
            Column(
                modifier = Modifier
                    .padding(top = 9.dp, bottom = 20.dp)
                    .background(Color.White)
                    .fillMaxWidth().wrapContentHeight()
                    .padding(10.dp),
            ) {
                Text(
                    text = selectedDay.value.toString().replace("-", "."),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .clickable { showDatePicker.value = true },
                    color = Color.Black,
                    style = Typography.bodyMedium,
                )

                Spacer(modifier = Modifier.padding(5.dp))

                AttendantRadioGroup(selectedAttendanceStatus.value, dayAttendance.value.attendance) {
                    selectedAttendanceStatus.value = it
                }
            }


            Text(
                modifier = Modifier.padding(start = 9.dp)
                    .clickable { showArrangeDropDown.value = !showArrangeDropDown.value },
                text = stringResource(R.string.arrange_text, arrangeEnum.value.data),
                style = Typography.labelMedium,
                color = Gray
            )

            if(showArrangeDropDown.value) {
                Box {
                    DropdownMenu(
                        expanded = showArrangeDropDown.value,
                        modifier = Modifier,
                        onDismissRequest = { showArrangeDropDown.value = false },
                        offset = DpOffset(10.dp, 0.dp)
                    ) {
                        ArrangeEnum.entries.forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = it.data,
                                        style = Typography.labelMedium,
                                        color = Color.Black
                                    )
                                },
                                onClick = {
                                    arrangeEnum.value = it
                                    showArrangeDropDown.value = false
                                }
                            )
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.padding(bottom = 9.dp))
        }



        if(selectedDay.value.dayOfWeek != DayOfWeek.SUNDAY) {
            item {
                Box(
                    modifier = Modifier.fillMaxSize().padding(top = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.not_today),
                        style = Typography.labelMedium,
                        color = TossBlue
                    )
                }

            }

            return@LazyColumn
        }

        itemsIndexed(dayMemberAttendances.value) { index, item ->
            MemberAttendanceBar(item, { changedState ->
                bottomSheetStateHolder.value = AttendanceSheetStateHolder(
                    true, item, changedState
                )
            }, {})
            Spacer(modifier = Modifier.height(3.dp))
        }
    }


    if(showDatePicker.value) {
        HomeDatePickerModal(
            { selectedDay.value = it },
            { showDatePicker.value = false }
        )
    }

    Log.d("HomeScreen", "showBottomSheet: ${bottomSheetStateHolder.value.showBottomSheet}")
    if(bottomSheetStateHolder.value.showBottomSheet) {
        val item = bottomSheetStateHolder.value.memberAttendance!!
        val changedState = bottomSheetStateHolder.value.selectedState!!
        AttendanceBottomSheet(item) {
            bottomSheetStateHolder.value = AttendanceSheetStateHolder(false)
            dayAttendanceViewModel.changeMemberAttendance(item, changedState)
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun Preview() {
//
//}