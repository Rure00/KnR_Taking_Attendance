package com.rure.knr_takingattendance.presentation.screen

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.data.entities.Position
import com.rure.knr_takingattendance.data.entities.getPositionFalseMap
import com.rure.knr_takingattendance.presentation.MainActivity
import com.rure.knr_takingattendance.presentation.intent.MemberIntent
import com.rure.knr_takingattendance.presentation.screen.pages.WriteBirthPage
import com.rure.knr_takingattendance.presentation.screen.pages.WriteJoiningDatePage
import com.rure.knr_takingattendance.presentation.screen.pages.WriteNamePage
import com.rure.knr_takingattendance.presentation.screen.pages.WritePhoneNumberPage
import com.rure.knr_takingattendance.presentation.screen.pages.WritePositionPage
import com.rure.knr_takingattendance.presentation.validation.MemberRegisterValidation
import com.rure.knr_takingattendance.presentation.viewmodels.MemberViewModel
import com.rure.knr_takingattendance.ui.theme.LightGray
import com.rure.knr_takingattendance.ui.theme.TossBlue
import com.rure.knr_takingattendance.ui.theme.Typography
import com.rure.knr_takingattendance.ui.theme.White
import java.time.LocalDate

@Composable
fun AddMemberScreen(
    toBack: () -> Unit,
    isAmend: Boolean = false,
    id: Int = -1,
    memberViewModel: MemberViewModel = viewModel(LocalContext.current as MainActivity)
) {

    val nameState = remember { mutableStateOf("") }
    val birthState = remember { mutableStateOf(LocalDate.now()) }
    val phoneNumberState = remember { mutableStateOf("") }
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
                activateNextButton.value = MemberRegisterValidation.checkPhoneNumber("010$it")
            }
        },
        {WritePositionPage(positionState.value) { position, isChecked ->
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

    LaunchedEffect(Unit) {
        if(isAmend) {
            val member = memberViewModel.getMemberById(id)
                ?: throw Exception("AddMemberScreen Has Id but isAmend Parameter is ${isAmend}")
            with(member) {
                nameState.value = name
                birthState.value = birth
                positionState.value = position
                phoneNumberState.value = phoneNumber.drop(3)
                joiningDayState.value = joinDate
            }

            activateNextButton.value = true
        }
    }

    fun toNextPage(context: Context) {
        if(!activateNextButton.value) return

        if(pageIndex.value < pages.lastIndex) {
            pageIndex.value++
        } else {
            if(isAmend) {
                val updated = memberViewModel.getMemberById(id)!!.copy(
                    name = nameState.value,
                    birth = birthState.value,
                    position = positionState.value,
                    joinDate = joiningDayState.value,
                    phoneNumber = "010" + phoneNumberState.value
                )
                memberViewModel.emit(MemberIntent.UpdateMember(updated))
            } else {
                memberViewModel.emit(
                    MemberIntent.SaveMember(
                        name = nameState.value,
                        birth = birthState.value,
                        position = positionState.value,
                        joinDate =joiningDayState.value,
                        phoneNumber = "010" + phoneNumberState.value,
                    )
                )
            }

            Toast.makeText(context, context.getString(R.string.success_save_member), Toast.LENGTH_SHORT).show()
            toBack()
        }

        activateNextButton.value = (isAmend)
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
            ).clickable {
                toNextPage(context) },
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




