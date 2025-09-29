package com.rure.presentation.screen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rure.presentation.R
import com.rure.presentation.component.MemberListItem
import com.rure.presentation.viewmodels.MemberViewModel
import com.rure.presentation.ui.theme.Black
import com.rure.presentation.ui.theme.TossBlue
import com.rure.presentation.ui.theme.Typography

@Composable
fun MemberListScreen(
    toDetail: (Int) -> Unit,
    memberViewModel: MemberViewModel
) {
    val memberList = memberViewModel.memberList.collectAsState()



    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Text(
                text = stringResource(R.string.member_num, memberList.value.size),
                style = Typography.titleMedium,
                color = Black,
                modifier = Modifier.padding(vertical = 14.dp, horizontal = 10.dp)
            )
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