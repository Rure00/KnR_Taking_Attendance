package com.rure.knr_takingattendance.presentation.screen

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.Position
import com.rure.knr_takingattendance.presentation.component.PositionButton
import com.rure.knr_takingattendance.presentation.state.UiResult
import com.rure.knr_takingattendance.presentation.state.detail.AttendanceHistory
import com.rure.knr_takingattendance.presentation.state.detail.DailyAttendance
import com.rure.knr_takingattendance.presentation.state.detail.YearlyAttendance
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import com.rure.knr_takingattendance.presentation.utils.RequestPermission
import com.rure.knr_takingattendance.presentation.utils.toPhoneFormat
import com.rure.knr_takingattendance.presentation.viewmodels.AttendanceHistoryViewModel
import com.rure.knr_takingattendance.ui.theme.Black
import com.rure.knr_takingattendance.ui.theme.Gray
import com.rure.knr_takingattendance.ui.theme.LightGray
import com.rure.knr_takingattendance.ui.theme.TossBlue
import com.rure.knr_takingattendance.ui.theme.Typography
import com.rure.knr_takingattendance.ui.theme.WarningRed
import com.rure.knr_takingattendance.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun AttendanceHistoryScreen(
    memberId: Int,
    context: Context = LocalContext.current,
    attendanceHistoryViewModel: AttendanceHistoryViewModel = hiltViewModel()
) {
    val tag = "MemberDetailScreen"
    val attendanceHistory = remember {
        mutableStateOf<AttendanceHistory?>(null)
    }
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            attendanceHistoryViewModel.attendanceHistory.collect {
                when(it) {
                    is UiResult.Success -> {
                        attendanceHistory.value = it.value
                    }
                    is UiResult.Fail-> {
                        Toast.makeText(context, context.getString(R.string.fail_load_atd_history), Toast.LENGTH_SHORT).show()
                        backPressedDispatcher?.onBackPressed()
                        return@collect
                    }
                    else -> null
                }
            }
        }

        attendanceHistoryViewModel.getHistory(memberId = memberId)
    }

    if(attendanceHistory.value == null) {
        HolderView()
        return
    }

    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .padding(top = 6.dp, start = 7.dp, end = 7.dp),
        state = listState,
        verticalArrangement = Arrangement.Top,
    ) {
        val history = attendanceHistory.value!!
        item {
            MemberInformationBox(history.member, history.attendanceRate)
            Spacer(modifier = Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                Text(
                    text = stringResource(R.string.late_num, history.lateNum),
                    style = Typography.labelSmall,
                    color = Gray
                )
                Text(
                    text = stringResource(R.string.forcible_num, history.forcibleNum),
                    style = Typography.labelSmall,
                    color = Gray
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        Log.d(tag, "yearlyAttendance num: ${history.yearlyAttendance.size}")
        itemsIndexed(history.yearlyAttendance) { index, item ->
            AttendanceHistoryBox(item)
        }
    }
}

@Composable
private fun MemberInformationBox(member: Member, atdRate: Int) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 7.dp, vertical = 7.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = member.name,
                style = Typography.bodyLarge,
                color = Black
            )

            Text(
                text = stringResource(R.string.join_date_in, member.joinDate.toString().replace("-", ".")),
                style = Typography.labelSmall,
                color = Gray
            )
        }
        Spacer(modifier = Modifier.height(15.dp))


        Row(
            modifier = Modifier.clickable {
                val requestCall = RequestPermission(context as Activity, context)
                requestCall.requestCall(member.phoneNumber)
            }
        ) {
            Image(
                painter = painterResource(R.drawable.phone_with_blue_bg),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(1.25.dp))
            Text(
                text = member.phoneNumber.toPhoneFormat(),
                style = Typography.bodySmall,
                color = Black,
                modifier = Modifier.drawBehind {

                    val strokeWidth = 1f
                    val y = size.height - strokeWidth / 2

                    drawLine(
                        Color.Black,
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(15.dp))


        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PositionBox(member)

            Column(
                modifier = Modifier
                    .background(color = TossBlue, shape = RoundedCornerShape(3.dp))
                    .padding(vertical = 8.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.attendance_str),
                    style = Typography.labelMedium,
                    color = White,
                )
                Text(
                    text = stringResource(R.string.rate, atdRate),
                    style = Typography.labelMedium,
                    color = White,
                )
            }

        }
    }
}

@Composable
private fun PositionBox(member: Member) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PositionButton(
                isSelected = member.position[Position.Forward] ?: false,
                position = Position.Forward,
                textStyle =  Typography.labelMedium,
            ) { _, _ -> }
            PositionButton(
                isSelected = member.position[Position.Defender] ?: false,
                position = Position.Defender,
                textStyle = Typography.labelMedium,

            ) { _, _ -> }
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PositionButton(
                isSelected = member.position[Position.Midfielder] ?: false,
                position = Position.Midfielder,
                textStyle = Typography.labelMedium,
            ) { _, _ -> }
            PositionButton(
                isSelected = member.position[Position.GoalKeeper] ?: false,
                position = Position.GoalKeeper,
                textStyle = Typography.labelMedium,
            ) { _, _ -> }
        }
    }
}

@Composable
private fun HolderView() {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth().height(200.dp).background(color = White).padding(10.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier.fillMaxWidth().height(140.dp).background(color = White).padding(10.dp)
        )
    }
}

@Composable
private fun AttendanceHistoryBox(yearlyAttendance: YearlyAttendance) {
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().background(color = White, shape = RoundedCornerShape(5.dp))
            .padding(6.dp),

    ) {
        Text(
            text = yearlyAttendance.year.toString(),
            style = Typography.bodyMedium,
            color = Black
        )
        Spacer(modifier = Modifier.height(12.dp))

        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            val availableWidth = maxWidth
            val itemSpacing = 8.dp
            val itemCount = 6

            val oneToSix = (1..6).map {
                yearlyAttendance.monthlyAttendances[it] ?: listOf()
            }
            val sevenToTwelve = (7..12).map {
                yearlyAttendance.monthlyAttendances[it] ?: listOf()
            }

            val itemWidth = (availableWidth - itemSpacing * (itemCount - 1)) / itemCount

            Column {
                MonthRowBox(oneToSix, 1, itemWidth, itemSpacing)
                Spacer(modifier = Modifier.height(6.dp))
                MonthRowBox(sevenToTwelve, 7, itemWidth, itemSpacing)
            }
        }
    }
}

@Composable
private fun MonthRowBox(list: List<List<DailyAttendance>>, firstMonth: Int, itemWidth: Dp, itemSpacing: Dp) {
    LazyRow(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(itemSpacing),
        userScrollEnabled = false
    ) {
        itemsIndexed(list) { index, item ->
            var attended = 0
            var tardyOrAbsence = 0
            item.forEach {
                when(it.attendance) {
                    AttendanceState.Attend -> attended++
                    AttendanceState.Tardy, AttendanceState.Absence -> tardyOrAbsence++
                    else -> null
                }
            }
            val bodyColor = if(item.isEmpty()) LightGray
                            else if(tardyOrAbsence == 0) TossBlue
                            else WarningRed

            Column(
                modifier = Modifier.width(itemWidth).background(color = bodyColor, shape = RoundedCornerShape(3.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.month_str, index + firstMonth),
                    style = Typography.bodyMedium,
                    color = White
                )
                Text(
                    text = "$attended/${item.size}",
                    style = Typography.bodyMedium,
                    color = White
                )
            }
        }
    }
}