package com.rure.knr_takingattendance.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rure.knr_takingattendance.presentation.state.home.AttendanceState
import com.rure.knr_takingattendance.ui.theme.Gray
import com.rure.knr_takingattendance.ui.theme.LightGray
import com.rure.knr_takingattendance.ui.theme.TossBlue
import com.rure.knr_takingattendance.ui.theme.Typography
import com.rure.knr_takingattendance.ui.theme.White

@Composable
fun AttendantRadioGroup(selected: AttendanceState, attendanceToNum: Map<AttendanceState, Int>, onSelect: (AttendanceState) -> Unit) {

    Row(
        modifier = Modifier.wrapContentHeight().fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AttendanceState.entries.forEach {
            AttendantToggleButton(
                state = it,
                num = attendanceToNum[it]!!,
                isChecked = selected == it,
                onClicked = onSelect
            )
        }
    }
}

@Composable
private fun AttendantToggleButton(state: AttendanceState, num: Int, isChecked: Boolean, onClicked: (AttendanceState) -> Unit) {
    val backgroundColor = if(isChecked) TossBlue else LightGray
    val textColor = if(isChecked) White else Gray
    val text = state.kr

    Column(
        modifier = Modifier.size(65.dp)
            .clip(shape = RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .clickable { onClicked(state) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = num.toString(),
            modifier = Modifier,
            textAlign = TextAlign.Center,
            style = Typography.bodySmall,
            color = textColor
        )

        Text(
            text = text,
            modifier = Modifier,
            textAlign = TextAlign.Center,
            style = Typography.bodySmall,
            color = textColor
        )
    }
}