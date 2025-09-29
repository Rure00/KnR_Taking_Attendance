package com.rure.presentation.screen.pages

import android.icu.number.FormattedNumber
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
    val regex = remember { Regex("^[0-9]*$") }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    val formatted = PhoneNumberUtils.formatNumber(phoneNumberState, "KR")
    val textValue = TextFieldValue(
        text = formatted,
        selection = TextRange(formatted.length)
    )

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {

        Text(
            text = stringResource(R.string.enter_phone_number),
            style = Typography.titleSmall,
            color = Black
        )

        Spacer(modifier = Modifier.height(12.dp).fillMaxWidth())

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.phone_head),
                style = Typography.bodyMedium,
                color = Black
            )
            BasicTextField(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().focusRequester(focusRequester).padding(8.dp),
                value = textValue,
                onValueChange = {
                    val onlyNumber = it.text.replace("-", "")
                    if(onlyNumber.length > 8 || !regex.matches(onlyNumber)) return@BasicTextField
                    Log.d("WritePhone", "text: ${it.text}, onlyNum: $onlyNumber")
                    onChange(onlyNumber)
                },
                textStyle = Typography.bodyMedium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true
            )
        }


        Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(LightGray))
    }
}