package com.rure.knr_takingattendance.presentation.component.home

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import com.rure.knr_takingattendance.presentation.state.home.DayMemberAttendance
import com.rure.knr_takingattendance.ui.theme.Black
import com.rure.knr_takingattendance.ui.theme.Gray
import com.rure.knr_takingattendance.ui.theme.LightGray
import com.rure.knr_takingattendance.ui.theme.SkyBlue
import com.rure.knr_takingattendance.ui.theme.TossBlue
import com.rure.knr_takingattendance.ui.theme.Typography
import com.rure.knr_takingattendance.ui.theme.White


private const val MaxDragOffset = -150f
private const val DragCompensation = 20f

@Composable
fun MemberAttendanceBar(
    memberAttendance: DayMemberAttendance,
    onStatusChange: (AttendanceState) -> Unit,
    onSlideLeft: () -> Unit
) {
    // 슬라이드 상태
    val dragOffset = remember { mutableStateOf(0f) }

    val dragOffsetAnimation = animateFloatAsState(
        targetValue = dragOffset.value,
        //tween(durationMillis = 200)
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow),
        label = "dragOffsetAnimation"
    )

    Box(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max)
        .background(Black)
        .pointerInput(Unit) {
            detectHorizontalDragGestures(
                onHorizontalDrag = { _, dragAmount ->
                    dragOffset.value = (dragOffset.value + dragAmount).coerceIn(MaxDragOffset, 0f)
                    //Log.d("MemberAttendanceBar", "slideOffset: ${dragOffset.value}")
                },
                onDragEnd = {
                    if(dragOffset.value < MaxDragOffset + DragCompensation) {
                        //TODO: 전화걸기
                        Log.d("MemberAttendanceBar", "full drag!")
                    } else {
                        Log.d("MemberAttendanceBar", "not full drag...")
                    }

                    dragOffset.value = 0f
                }
            )
        })
    {

        Row(modifier = Modifier
            .fillMaxHeight().fillMaxWidth()
            //.offset { IntOffset(-(dragOffsetAnimation.value + MaxDragOffset).toInt(), 0) }
            .background(SkyBlue),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.phone_image),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                text = stringResource(R.string.call),
                style = Typography.bodyMedium,
                color = White
            )
        }

        MemberStatusBar(dragOffsetAnimation.value, memberAttendance, onStatusChange)
    }


}

@Composable
private fun MemberStatusBar(animationOffset: Float, memberAttendance: DayMemberAttendance, onClick: (AttendanceState) -> Unit) {
    Row(
        modifier = Modifier
            .offset(x = animationOffset.dp)
            .fillMaxWidth().fillMaxHeight()
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

        IconButton(onClick = { onClick(AttendanceState.Attend) }) {
            Image(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            modifier = Modifier.padding(start = 16.dp).weight(1f),
            text = memberAttendance.name,
            style = Typography.bodyMedium
        )

        Text(
            text = memberAttendance.attendanceStatus.kr,
            style = Typography.bodyLarge,
            color =textColor
        )
    }
}

@Composable
private fun ChangeStatusBar(onStatusChange: (AttendanceState) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .background(LightGray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AttendanceState.entries.forEach {
            if(it != AttendanceState.All) {
                val (iconId, textColor) = when(it) {
                    AttendanceState.Attend -> R.drawable.attendant_icon to TossBlue
                    AttendanceState.NonAttend -> R.drawable.non_attendant_icon to Gray
                    AttendanceState.Tardy -> R.drawable.tardy_icon to Gray
                    AttendanceState.Absence -> R.drawable.absence_icon to Red
                    else -> null
                }!!

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .weight(1f)
                        .clickable { onStatusChange(it) }
                        .background(White)
                        .padding(vertical = 11.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(iconId),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = it.kr,
                        style = Typography.bodySmall,
                        color = textColor
                    )
                }
            }
        }


    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun Preview() {
//    val ma = DayMemberAttendance(
//        name = "홍길동", localDate = LocalDate.now(), attendanceStatus = AttendanceState.Absence
//    )
//    MemberAttendanceBar(ma, {}, {})
//}