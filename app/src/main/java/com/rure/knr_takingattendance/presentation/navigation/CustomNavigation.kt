package com.rure.knr_takingattendance.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = Destination.Home.label) {
        mainNavGraph(navController)
    }
}

private fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation(
        route = "",
        startDestination = Destination.Home.label
    ) {
        composable(route = Destination.Home.label) {
//            ChatListFragment (
//                openChatRoom = { roomId ->
//                    navController.navigate(route = Destination.ChatList.ChatRoom.route+roomId) {
//                        popUpTo(navController.graph.id) {inclusive=false}
//                    }
//                }
//            )
        }

        composable(route = Destination.PersonalAttend.label) {

        }

        composable(route = Destination.AddMember.label) {

        }

        composable(route = Destination.Option.label) {

        }
    }
}