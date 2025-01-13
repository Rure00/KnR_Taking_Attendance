package com.rure.knr_takingattendance.presentation.screen

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.data.entities.Member
import com.rure.knr_takingattendance.data.entities.Position
import com.rure.knr_takingattendance.data.entities.getPositionFalseMap
import com.rure.knr_takingattendance.presentation.component.KoreanTextField
import com.rure.knr_takingattendance.presentation.component.Picker
import com.rure.knr_takingattendance.presentation.component.PositionButton
import com.rure.knr_takingattendance.presentation.component.option.BirthPicker
import com.rure.knr_takingattendance.presentation.intent.MemberIntent
import com.rure.knr_takingattendance.presentation.validation.MemberRegisterValidation
import com.rure.knr_takingattendance.presentation.viewmodels.MemberViewModel
import com.rure.knr_takingattendance.ui.theme.Black
import com.rure.knr_takingattendance.ui.theme.LightGray
import com.rure.knr_takingattendance.ui.theme.TossBlue
import com.rure.knr_takingattendance.ui.theme.Typography
import com.rure.knr_takingattendance.ui.theme.White
import java.time.LocalDate

@Composable
fun AddMemberScreen(
    toBack: () -> Unit,
    memberViewModel: MemberViewModel = hiltViewModel<MemberViewModel>()
) {

    val nameState = remember { mutableStateOf("") }
    val birthState = remember { mutableStateOf(LocalDate.now()) }
    val phoneNumberState = remember { mutableStateOf("010") }
    val positionState = remember { mutableStateOf(getPositionFalseMap()) }
    val joiningDayState = remember { mutableStateOf(LocalDate.now()) }
    val activateNextButton = remember { mutableStateOf(false) }

    val pageIndex = remember { mutableStateOf(0) }
    val pages = listOf<@Composable () -> Unit>(
        { WriteNamePage(nameState.value) {
            nameState.value = it
            activateNextButton.value = MemberRegisterValidation.checkName(it)
        } },
        { WriteBirthPage(birthState.value) {
            birthState.value = it
            activateNextButton.value = true
        } },
        {
            WritePhoneNumberPage(phoneNumberState.value) {
                phoneNumberState.value = it
                activateNextButton.value = MemberRegisterValidation.checkPhoneNumber(it)
            }
        },
        {WritePositionPage { position, isChecked ->
                positionState.value = positionState.value.toMutableMap().apply {
                    this[position] = isChecked
                }

                activateNextButton.value = positionState.value.values.any { it }
        } },
        { WriteJoiningDatePage(joiningDayState.value) {
            joiningDayState.value = it
            activateNextButton.value = true
        } }

    )

    fun toNextPage(context: Context) {
        if(pageIndex.value < pages.lastIndex) {
            pageIndex.value++
        } else {
            memberViewModel.emit(
                MemberIntent.SaveMember(
                    name = nameState.value,
                    birth = birthState.value,
                    position = positionState.value,
                    joinDate =joiningDayState.value,
                    phoneNumber = phoneNumberState.value,
                )
            )

            Toast.makeText(context, context.getString(R.string.success_save_member), Toast.LENGTH_SHORT).show()
            toBack()
        }

        activateNextButton.value = false
    }

    BackHandler {
        if(pageIndex.value > 0) pageIndex.value--
        else toBack()
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val context = LocalContext.current


        Spacer(modifier = Modifier.height(30.dp))
        Box(modifier = Modifier.weight(1f).fillMaxSize()) {
            pages[pageIndex.value]()
        }
        Box(
            modifier = Modifier.height(55.dp).fillMaxWidth().background(
                if(activateNextButton.value) TossBlue
                else LightGray
            ).clickable { toNextPage(context) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = "확인",
                style = Typography.bodyMedium,
                color = White,
                textAlign = TextAlign.Center
            )
        }
    }


}

@Composable
private fun WriteNamePage(nameState: String, onChange: (String) -> Unit) {
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
        )
    }
}

@Composable
private fun WriteBirthPage(birthState: LocalDate, onChange: (LocalDate) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        val selectedDate = remember {
            mutableStateOf(birthState)
        }

        Text(
            text = stringResource(R.string.enter_birth),
            style = Typography.titleSmall,
            color = Black
        )

        Spacer(modifier = Modifier.height(6.dp))

        BirthPicker(
            date = selectedDate.value,
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

@Composable
private fun WritePhoneNumberPage(phoneNumberState: String, onChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            text = stringResource(R.string.enter_phone_number),
            style = Typography.titleSmall,
            color = Black
        )

        Spacer(modifier = Modifier.height(12.dp).fillMaxWidth())

        BasicTextField(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp),
            value = phoneNumberState,
            onValueChange = { onChange(it) },
            textStyle = Typography.bodyMedium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(LightGray))
    }
}

@Composable
private fun WritePositionPage(onChange: (Position, Boolean) -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Text(
            text = stringResource(R.string.enter_position),
            style = Typography.titleSmall,
            color = Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        val textStyle = Typography.bodySmall
        val modifier = Modifier.height(40.dp).fillMaxWidth()

        Row(
            //modifier = Modifier.padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            PositionButton(
                Position.Forward, textStyle, modifier.weight(1f)
            ) { isChecked, position ->
                onChange(position, isChecked)
            }

            PositionButton(
                Position.Midfielder, textStyle, modifier.weight(1f)
            ) { isChecked, position ->
                onChange(position, isChecked)
            }

            PositionButton(
                Position.Defender, textStyle, modifier.weight(1f)
            ) { isChecked, position ->
                onChange(position, isChecked)
            }

            PositionButton(
                Position.GoalKeeper, textStyle, modifier.weight(1f)
            ) { isChecked, position ->
                onChange(position, isChecked)
            }
        }
    }

}

@Composable
private fun WriteJoiningDatePage(joiningDateState: LocalDate, onChange: (LocalDate) -> Unit) {
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

        BirthPicker(
            date = selectedDate.value,
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


