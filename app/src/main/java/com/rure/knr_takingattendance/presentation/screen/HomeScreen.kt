package com.rure.knr_takingattendance.presentation.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.presentation.component.home.AttendanceBottomSheet
import com.rure.knr_takingattendance.presentation.component.home.AttendantRadioGroup
import com.rure.knr_takingattendance.presentation.component.home.HomeDatePickerModal
import com.rure.knr_takingattendance.presentation.component.home.MemberAttendanceBar
import com.rure.knr_takingattendance.presentation.intent.ParticipationIntent
import com.rure.knr_takingattendance.presentation.state.home.ArrangeEnum
import com.rure.knr_takingattendance.presentation.state.home.AttendanceSheetStateHolder
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import com.rure.knr_takingattendance.presentation.state.home.DayAttendanceSummary
import com.rure.knr_takingattendance.presentation.viewmodels.DayAttendanceViewModel
import com.rure.knr_takingattendance.ui.theme.Gray
import com.rure.knr_takingattendance.ui.theme.TossBlue
import com.rure.knr_takingattendance.ui.theme.Typography
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.DayOfWeek


@Composable
fun HomeScreen(
    toPersonal: () -> Unit,
    dayAttendanceViewModel: DayAttendanceViewModel = hiltViewModel<DayAttendanceViewModel>()
) {
    val showDatePicker = remember { mutableStateOf(false) }
    val selectedDay = remember {
        dayAttendanceViewModel.selectedDay
    }

    val selectedAttendanceStatus = remember {
        mutableStateOf(AttendanceState.All)
    }
    val dayAttendance = dayAttendanceViewModel.daySummarize.collectAsState()

    val dayMemberAttendances = dayAttendanceViewModel.memberParticipation.collectAsState()

    val listState = rememberLazyListState()

    val showArrangeDropDown = remember { mutableStateOf(false) }
    val arrangeEnum = remember {
        mutableStateOf<ArrangeEnum>(ArrangeEnum.Name)
    }

    val bottomSheetStateHolder = remember { mutableStateOf(AttendanceSheetStateHolder(false)) }

    LaunchedEffect(true) {
        dayAttendanceViewModel.emit(ParticipationIntent.InitParticipation)
    }


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

                AttendantRadioGroup(selectedAttendanceStatus.value, dayAttendance.value) {
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
            { dayAttendanceViewModel.changeSelectedDay(it) },
            { showDatePicker.value = false }
        )
    }

    Log.d("HomeScreen", "showBottomSheet: ${bottomSheetStateHolder.value.showBottomSheet}")
    if(bottomSheetStateHolder.value.showBottomSheet) {
        val item = bottomSheetStateHolder.value.participation!!
        val changedState = bottomSheetStateHolder.value.selectedState!!
        AttendanceBottomSheet(item) {
            bottomSheetStateHolder.value = AttendanceSheetStateHolder(false)
            dayAttendanceViewModel.emit(
                ParticipationIntent.UpdateParticipation(item.copy(attendanceStatus = changedState))
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun Preview() {
//
//}