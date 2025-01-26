package com.rure.knr_takingattendance.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.presentation.MainActivity
import com.rure.knr_takingattendance.presentation.component.MemberListItem
import com.rure.knr_takingattendance.presentation.viewmodels.MemberViewModel
import com.rure.knr_takingattendance.ui.theme.TossBlue
import com.rure.knr_takingattendance.ui.theme.Typography

@Composable
fun MemberListScreen(
    toDetail: (Int) -> Unit,
    memberViewModel: MemberViewModel = viewModel(LocalContext.current as MainActivity)
) {
    val memberList = memberViewModel.memberList.collectAsState()



    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp))
        }

        itemsIndexed(memberList.value) { index, member ->
            MemberListItem(
                member = member,
                toDetail = toDetail,
            )
        }
    }

    if(memberList.value.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.no_member_str),
                style = Typography.bodyLarge,
                color = TossBlue
            )
        }
    }
}