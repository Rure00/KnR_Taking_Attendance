package com.rure.knr_takingattendance.presentation.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.Position
import com.rure.knr_takingattendance.presentation.MainActivity
import com.rure.knr_takingattendance.presentation.component.PositionButton
import com.rure.knr_takingattendance.presentation.utils.RequestPermission
import com.rure.knr_takingattendance.presentation.utils.toPhoneFormat
import com.rure.knr_takingattendance.presentation.viewmodels.MemberViewModel
import com.rure.knr_takingattendance.ui.theme.Black
import com.rure.knr_takingattendance.ui.theme.Gray
import com.rure.knr_takingattendance.ui.theme.Typography

@Composable
fun MemberDetailScreen(
    memberId: Int,
    memberViewModel: MemberViewModel = viewModel(LocalContext.current as MainActivity)
) {
    val tag = "MemberDetailScreen"
    val member = remember { memberViewModel.getMemberById(memberId)?: throw Exception("$tag: Wrong Member Id") }

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            MemberInformationBox(member)
            Spacer(modifier = Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                val lateNum = 5
                val forcibleNum = 5
                Text(
                    text = stringResource(R.string.late_num, lateNum),
                    style = Typography.labelSmall,
                    color = Gray
                )
                Text(
                    text = stringResource(R.string.forcible_num, forcibleNum),
                    style = Typography.labelSmall,
                    color = Gray
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
        }




    }
}

@Composable
private fun MemberInformationBox(member: Member) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .padding(top = 6.dp, start = 7.dp, end = 7.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 7.dp, vertical = 7.dp)
    ) {
        Row(
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
                style = Typography.bodyLarge,
                color = Gray
            )
        }


        Row(
            modifier = Modifier.clickable {
                val requestCall = RequestPermission(context as Activity, context)
                requestCall.requestCall(member.phoneNumber)
            }
        ) {
            Image(
                painter = painterResource(R.drawable.phone_with_blue_bg),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(1.25.dp))
            Text(
                text = member.phoneNumber.toPhoneFormat(),
                style = Typography.labelSmall,
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

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            val attendanceRate = 80
            PositionBox(member)
            Text(
                text = stringResource(R.string.attendance_persentage, attendanceRate),

                )
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
                Position.Forward, Typography.labelSmall,
                initialState = member.position[Position.Forward] ?: false
            ) { _, _ -> }
            PositionButton(
                Position.Defender, Typography.labelSmall,
                initialState = member.position[Position.Defender] ?: false
            ) { _, _ -> }
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PositionButton(
                Position.Midfielder, Typography.labelSmall,
                initialState = member.position[Position.Midfielder] ?: false
            ) { _, _ -> }
            PositionButton(
                Position.GoalKeeper, Typography.labelSmall,
                initialState = member.position[Position.GoalKeeper] ?: false
            ) { _, _ -> }
        }
    }
}