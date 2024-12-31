package com.rure.knr_takingattendance.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.rure.knr_takingattendance.presentation.navigation.Destination
import com.rure.knr_takingattendance.presentation.component.TopAppBarComponent
import com.rure.knr_takingattendance.presentation.navigation.mainNavGraph
import com.rure.knr_takingattendance.ui.theme.KnR_TakingAttendanceTheme
import com.rure.knr_takingattendance.ui.theme.LightGray
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KnR_TakingAttendanceTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                        .imePadding()
                ) {
                    MainPage()
                }
            }
        }
    }
}

@Composable
private fun MainPage() {
    val navController = rememberNavController()
    val screen: MutableState<Destination> = remember {
        mutableStateOf(Destination.Home)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGray,
        topBar = { TopAppBarComponent(navController, screen.value) }
    ) { innerPadding ->
        val pagerState = rememberPagerState(0, 0f) {
            Destination::class.nestedClasses.size
        }

        HorizontalPager(
            modifier = Modifier.padding(innerPadding),
            state = pagerState,
            userScrollEnabled = false
        ) {
            NavHost(
                navController,
                startDestination = "main/") {
                mainNavGraph(navController) {
                    screen.value = it
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    KnR_TakingAttendanceTheme {
        MainPage()
    }
}