package com.rure.knr_takingattendance.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rure.knr_takingattendance.ui.theme.LightGray
import com.rure.knr_takingattendance.ui.theme.Typography

@Composable
fun KoreanTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = Typography.bodyMedium
) {
    val regex = Regex("^[가-힣ㆍᆞᆢㄱ-ㅎㅏ-ㅣ]*$")

    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || regex.matches(newValue)) {
                onValueChange(newValue)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        textStyle = textStyle,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )

    Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(LightGray))
}