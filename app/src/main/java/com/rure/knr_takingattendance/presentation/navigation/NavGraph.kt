package com.rure.knr_takingattendance.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.rure.knr_takingattendance.presentation.screen.AddMemberScreen
import com.rure.knr_takingattendance.presentation.screen.HomeScreen
import com.rure.knr_takingattendance.presentation.screen.AttendanceHistoryScreen
import com.rure.knr_takingattendance.presentation.screen.OptionScreen

fun NavGraphBuilder.mainNavGraph(navController: NavController, onScreenChanged: (Destination) -> Unit) {
    navigation(
        route = "main/",
        startDestination = Destination.Home.route
    ) {
        composable(route = Destination.Home.route) {
            HomeScreen(
                toAttendanceHistoryScreen = { navController.navigate(Destination.MemberDetail.route + "/${it}") }
            )
            onScreenChanged(Destination.Home)
        }

        composable(
            route = Destination.MemberDetail.route + "/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            val id = it.arguments?.getInt("id") ?: throw  Exception("No Arguments For id.")
            AttendanceHistoryScreen(id)
            onScreenChanged(Destination.MemberDetail)
        }

        composable(route = Destination.AddMember.route) {
            AddMemberScreen(
                { navController.popBackStack() }
            )
            onScreenChanged(Destination.AddMember)
        }

        composable(route = Destination.Option.route) {
            OptionScreen(
                toAddMember = { navController.navigate(Destination.AddMember.route) },
                toSaveAttendance = {  }
            )
            onScreenChanged(Destination.Option)
        }
    }
}