package com.rure.knr_takingattendance.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.data.entities.Position
import com.rure.knr_takingattendance.presentation.component.KoreanTextField
import com.rure.knr_takingattendance.presentation.component.Picker
import com.rure.knr_takingattendance.presentation.component.option.BirthPicker
import com.rure.knr_takingattendance.presentation.validation.MemberRegisterValidation
import com.rure.knr_takingattendance.presentation.viewmodels.MemberViewModel
import com.rure.knr_takingattendance.ui.theme.Black
import com.rure.knr_takingattendance.ui.theme.Typography
import java.time.LocalDate

@Composable
fun AddMemberScreen(
    memberViewModel: MemberViewModel = hiltViewModel<MemberViewModel>()
) {
    val pageIndex = remember { mutableStateOf(0) }
    val nameState = remember { mutableStateOf("") }
    val birthState = remember { mutableStateOf(LocalDate.now()) }
    val positionState = remember { mutableStateOf(listOf<Position>()) }
    val joiningDayState = remember { mutableStateOf(LocalDate.now()) }
    val activateNextButton = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        WriteBirthPage(birthState) { }

//        when(pageIndex.value) {
//            0 -> WriteNamePage(nameState) { activateNextButton.value = MemberRegisterValidation.checkName(it) }
//            1 -> WriteText("이름") { activateNextButton.value = MemberRegisterValidation.checkName(nameState.value) }
//            2 -> WriteText("이름") { }
//            3 -> WriteText("이름") { }
//            4 -> WriteText("이름") { }
//            else -> { pageIndex.value = 0 }
//        }
    }
}

@Composable
private fun WriteNamePage(nameState: MutableState<String>, onChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            text = stringResource(R.string.enter_name),
            style = Typography.bodyLarge,
            color = Black
        )

        KoreanTextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
        )

        onChange(nameState.value)
    }
}

@Composable
private fun WriteBirthPage(birthState: MutableState<LocalDate>, onChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        val selectedDate = remember {
            mutableStateOf(LocalDate.now())
        }

        BirthPicker(selectedDate.value) {
            selectedDate.value = it
        }

        Text(text = selectedDate.value.toString())
    }
}




