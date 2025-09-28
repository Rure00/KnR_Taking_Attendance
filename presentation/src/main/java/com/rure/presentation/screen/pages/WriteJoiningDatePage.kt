package com.rure.knr_takingattendance.presentation.screen.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.presentation.component.option.DatePicker
import com.rure.knr_takingattendance.ui.theme.Black
import com.rure.knr_takingattendance.ui.theme.LightGray
import com.rure.knr_takingattendance.ui.theme.Typography
import java.time.LocalDate


@Composable
 fun WriteJoiningDatePage(joiningDateState: LocalDate, onChange: (LocalDate) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        val selectedDate = remember {
            mutableStateOf(joiningDateState)
        }

        Text(
            text = stringResource(R.string.enter_joining_date),
            style = Typography.titleSmall,
            color = Black
        )

        Spacer(modifier = Modifier.height(6.dp))

        DatePicker(
            selectedDate = selectedDate.value,
            itemTextStyle = Typography.titleLarge,
            modifier = Modifier,
            itemModifier = Modifier,
            visibleItemNum = 5,
            dividerColor = LightGray
        ) {
            selectedDate.value = it
            onChange(it)
        }
    }
}