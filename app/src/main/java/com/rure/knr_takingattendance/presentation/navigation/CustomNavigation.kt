package com.rure.knr_takingattendance.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.ui.theme.KnR_TakingAttendanceTheme
import com.rure.knr_takingattendance.ui.theme.TossBlue
import com.rure.knr_takingattendance.ui.theme.Typography
import com.rure.knr_takingattendance.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavigation() {
    val navController = rememberNavController()
    val screen = remember {
        Destination.Home
    }
    val topAppBarColors = remember {
        TopAppBarColors(
            containerColor = TossBlue,
            scrolledContainerColor = White,
            navigationIconContentColor = White,
            titleContentColor = White,
            actionIconContentColor = White
        )
    }

    NavHost(
        navController,
        startDestination = "main/") {
        mainNavGraph(navController)
    }

    Row(
        modifier = Modifier.fillMaxWidth()
            .height(65.dp)
            .background(TossBlue),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Image(
                painter = painterResource(R.drawable.back_arrow),
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }

        Text(
            text = screen.label,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f),
            style = Typography.titleMedium,
            color = White
        )

        IconButton(onClick = {
            navController.navigate(Destination.Option.route) {
                popUpTo(navController.graph.id) { inclusive=true }
            }
        }) {
            Image(
                painter = painterResource(R.drawable.option_img),
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }
    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NavigationPreview() {
    CustomNavigation()
}


private fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation(
        route = "main/",
        startDestination = Destination.Home.route
    ) {
        composable(route = Destination.Home.route) {
//            ChatListFragment (
//                openChatRoom = { roomId ->
//                    navController.navigate(route = Destination.ChatList.ChatRoom.route+roomId) {
//                        popUpTo(navController.graph.id) {inclusive=false}
//                    }
//                }
//            )
        }

        composable(route = Destination.PersonalAttend.route) {

        }

        composable(route = Destination.AddMember.route) {

        }

        composable(route = Destination.Option.route) {

        }
    }
}