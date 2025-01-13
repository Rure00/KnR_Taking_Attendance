package com.rure.knr_takingattendance.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.presentation.viewmodels.DayAttendanceViewModel
import com.rure.knr_takingattendance.presentation.viewmodels.MemberViewModel
import com.rure.knr_takingattendance.ui.theme.LightGray
import com.rure.knr_takingattendance.ui.theme.Typography
import com.rure.knr_takingattendance.ui.theme.White

@Composable
fun OptionScreen(
    toAddMember: () -> Unit,
    toSaveAttendance: () -> Unit,
    memberViewModel: MemberViewModel = hiltViewModel<MemberViewModel>()
) {
    Column(
        modifier = Modifier.fillMaxSize().background(LightGray).padding(top = 6.dp, start = 6.dp, end = 6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        OptionButton(stringResource(R.string.add_member)) { toAddMember() }
        OptionButton(stringResource(R.string.save_attendance)) { toSaveAttendance() }
    }
}


@Composable
private fun OptionButton(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        shape = RoundedCornerShape(7.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier.clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 15.dp, bottom = 15.dp).weight(1f),
                text = text,
                style = Typography.bodyMedium,
            )

            Image(
                painter = painterResource(R.drawable.next_arrow),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }

}

//@Preview
//@Composable
//fun PreviewOptionButton() {
//    OptionButton("팀원 추가") { }
//}
