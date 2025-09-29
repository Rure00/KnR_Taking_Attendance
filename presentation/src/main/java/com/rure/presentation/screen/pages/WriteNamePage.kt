package com.rure.presentation.screen.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.presentation.component.KoreanTextField
import com.rure.knr_takingattendance.ui.theme.Black
import com.rure.knr_takingattendance.ui.theme.Typography

@Composable
fun WriteNamePage(nameState: String, onChange: (String) -> Unit) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }


    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            text = stringResource(R.string.enter_name),
            style = Typography.titleSmall,
            color = Black
        )

        Spacer(modifier = Modifier.height(12.dp).fillMaxWidth())

        KoreanTextField(
            value = nameState,
            onValueChange = { onChange(it) },
            modifier = Modifier.focusRequester(focusRequester)
        )
    }
}