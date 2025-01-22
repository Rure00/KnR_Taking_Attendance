package com.rure.knr_takingattendance.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rure.knr_takingattendance.presentation.MainActivity
import com.rure.knr_takingattendance.presentation.viewmodels.MemberViewModel

@Composable
fun MemberDetailScreen(
    memberViewModel: MemberViewModel = viewModel(LocalContext.current as MainActivity)
) {

}