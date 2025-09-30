package com.rure.presentation.screen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rure.presentation.R
import com.rure.domain.models.MemberParticipation
import com.rure.presentation.component.home.AttendanceBottomSheet
import com.rure.presentation.component.home.AttendantRadioGroup
import com.rure.presentation.component.home.HomeDatePickerModal
import com.rure.presentation.component.home.MemberAttendanceBar
import com.rure.presentation.intent.ParticipationIntent
import com.rure.presentation.state.home.ArrangeEnum
import com.rure.presentation.state.home.AttendanceSheetStateHolder
import com.rure.domain.models.AttendanceState
import com.rure.presentation.viewmodels.DayAttendanceViewModel
import com.rure.presentation.ui.theme.Gray
import com.rure.presentation.ui.theme.TossBlue
import com.rure.presentation.ui.theme.Typography
import com.rure.presentation.ui.theme.White


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    toAttendanceHistoryScreen: (Int) -> Unit,
    context: Context = LocalContext.current,
    dayAttendanceViewModel: DayAttendanceViewModel
) {
    val showDatePicker = remember { mutableStateOf(false) }
    val selectedDay = remember {
        dayAttendanceViewModel.selectedDay
    }

    val daySummarize = dayAttendanceViewModel.daySummarize.collectAsState()
    val selectedAttendanceStatus = remember {
        mutableStateOf(AttendanceState.All)
    }

    val listState = rememberLazyListState()

    val showArrangeDropDown = remember { mutableStateOf(false) }
    val arrangeEnum = remember {
        mutableStateOf<ArrangeEnum>(ArrangeEnum.Name)
    }

    val dayMemberAttendances = dayAttendanceViewModel.memberParticipation
        .collectAsState().value.let { list ->
            when(arrangeEnum.value) {
                ArrangeEnum.Name -> {
                    list.sortedBy { it.member.name }
                }
                ArrangeEnum.AttendanceState -> {
                    list.sortedBy { it.attendanceStatus }
                }
            }
        }

    val callPermissionState = callPermissionState()
    val permissionRequester = requestPermission (
        permissions = PERMISSIONS.CALL_PHONE,
        onGranted = { },
        onRefused = {
            Toast.makeText(context, "권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    )


    val bottomSheetStateHolder = remember { mutableStateOf(AttendanceSheetStateHolder(false)) }

    LazyColumn(
        state = listState,
    ) {
        item {
            Column(
                modifier = Modifier
                    .padding(top = 9.dp, bottom = 20.dp)
                    .background(Color.White)
                    .fillMaxWidth().wrapContentHeight()
                    .padding(10.dp),
            ) {
                Text(
                    text = selectedDay.value.toString().replace("-", "."),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .clickable { showDatePicker.value = true },
                    color = Color.Black,
                    style = Typography.bodyMedium,
                )

                Spacer(modifier = Modifier.padding(5.dp))

                AttendantRadioGroup(selectedAttendanceStatus.value, daySummarize.value) {
                    selectedAttendanceStatus.value = it
                }
            }


            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            ) {
                Text(
                    modifier = Modifier.padding(start = 9.dp)
                        .clickable { showArrangeDropDown.value = !showArrangeDropDown.value }
                        .weight(1f),
                    text = stringResource(R.string.arrange_text, arrangeEnum.value.data),
                    style = Typography.labelMedium,
                    color = Gray
                )

                Text(
                    text = stringResource(R.string.delete_activity),
                    style = Typography.labelMedium,
                    color = Gray,
                    modifier = Modifier.padding(end = 9.dp).clickable {
                        dayAttendanceViewModel.emit(ParticipationIntent.DeleteActivityDate)
                    }
                )
            }


            if(showArrangeDropDown.value) {
                Box {
                    DropdownMenu(
                        expanded = showArrangeDropDown.value,
                        onDismissRequest = { showArrangeDropDown.value = false },
                        offset = DpOffset(10.dp, 0.dp)
                    ) {
                        ArrangeEnum.entries.forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = it.data,
                                        style = Typography.labelMedium,
                                        color = Color.Black
                                    )
                                },
                                onClick = {
                                    arrangeEnum.value = it
                                    showArrangeDropDown.value = false
                                }
                            )
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.padding(bottom = 9.dp))
        }

        if(dayMemberAttendances.isEmpty()) return@LazyColumn

        itemsIndexed(getAttendanceByStatus(dayMemberAttendances, selectedAttendanceStatus.value)) { index, item ->
            MemberAttendanceBar(
                item,
                { toAttendanceHistoryScreen(it) },
                { changedState ->
                    bottomSheetStateHolder.value = AttendanceSheetStateHolder(
                        true, item, changedState
                    )
                },
                {
                    if(!callPermissionState.allPermissionsGranted) {
                        permissionRequester.launchMultiplePermissionRequest()
                        return@MemberAttendanceBar
                    }
                    val callIntent = Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:${item.member.phoneNumber}"));
                    context.startActivity(callIntent);
                }
            )
            Spacer(modifier = Modifier.height(3.dp))
        }
    }


    if(showDatePicker.value) {
        HomeDatePickerModal(
            { dayAttendanceViewModel.changeSelectedDay(it) },
            { showDatePicker.value = false }
        )
    }

    if(bottomSheetStateHolder.value.showBottomSheet) {
        val item = bottomSheetStateHolder.value.participation!!
        AttendanceBottomSheet(item) {
            bottomSheetStateHolder.value = AttendanceSheetStateHolder(false)
            dayAttendanceViewModel.emit(
                ParticipationIntent.SaveParticipation(item.copy(attendanceStatus = it))
            )
        }
    }

    if(dayMemberAttendances.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 10.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.padding(top = 60.dp).weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.not_today),
                    style = Typography.labelMedium,
                    color = TossBlue
                )
            }

            Text(
                text = stringResource(R.string.start_attendance),
                style = Typography.bodyLarge,
                color = White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth().wrapContentHeight()
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = TossBlue)
                    .clickable {
                        dayAttendanceViewModel.emit(ParticipationIntent.CreateActivityDate)
                    }
                    .padding(vertical = 10.dp)
            )
        }
    }
}

private fun getAttendanceByStatus(list: List<MemberParticipation>, status: AttendanceState) =
    if(status == AttendanceState.All) {
        list
    } else {
        list.filter {
            it.attendanceStatus == status
        }
    }
//
//class RequestPermission(
//    private val activity: Activity,
//    private val context: Context
//) {
//    private val callPermissionCode = 1000
//    private val callPermissions =  arrayOf(Manifest.permission.CALL_PHONE)
//
//    fun checkCallPermission() = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
//
//    fun requestCall(telNum: String) {
//        if (checkCallPermission()) {
//            ActivityCompat.requestPermissions(activity, callPermissions, callPermissionCode);
//        } else {
//            val callIntent = Intent(Intent.ACTION_CALL);
//            callIntent.setData(Uri.parse("tel:$telNum"));
//            context.startActivity(callIntent);
//        }
//    }
//}

private object PERMISSIONS {
    val CALL_PHONE: List<String> = mutableListOf<String>().apply {
        add(Manifest.permission.CALL_PHONE)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun callPermissionState() = rememberMultiplePermissionsState(PERMISSIONS.CALL_PHONE)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun requestPermission(
    permissions: List<String>,
    onGranted: () -> Unit,
    onRefused: (List<String>) -> Unit
) = rememberMultiplePermissionsState(permissions) { permissionToIsGranted ->
    if(!permissionToIsGranted.containsValue(false)) {
        onGranted()
    } else {
        val refusedList = permissionToIsGranted.entries.filter { !it.value }.map { it.key }
        onRefused(refusedList)
    }
}