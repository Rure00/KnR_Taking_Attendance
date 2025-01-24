package com.rure.knr_takingattendance.presentation.screen.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.ui.theme.Black
import com.rure.knr_takingattendance.ui.theme.LightGray
import com.rure.knr_takingattendance.ui.theme.Typography


@Composable
fun WritePhoneNumberPage(phoneNumberState: String, onChange: (String) -> Unit) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    val textValue = TextFieldValue(phoneNumberState, selection = TextRange(phoneNumberState.length))

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            text = stringResource(R.string.enter_phone_number),
            style = Typography.titleSmall,
            color = Black
        )

        Spacer(modifier = Modifier.height(12.dp).fillMaxWidth())

        BasicTextField(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().focusRequester(focusRequester).padding(8.dp),
            value = textValue,
            onValueChange = { onChange(it.text) },
            textStyle = Typography.bodyMedium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(LightGray))
    }
}