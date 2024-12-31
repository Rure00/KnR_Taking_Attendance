package com.rure.knr_takingattendance.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

fun NavGraphBuilder.mainNavGraph(navController: NavController, onScreenChanged: (Destination) -> Unit) {
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

            onScreenChanged(Destination.Home)
        }

        composable(route = Destination.PersonalAttend.route) {
            onScreenChanged(Destination.PersonalAttend)
        }

        composable(route = Destination.AddMember.route) {
            onScreenChanged(Destination.AddMember)
        }

        composable(route = Destination.Option.route) {
            onScreenChanged(Destination.Option)
        }
    }
}