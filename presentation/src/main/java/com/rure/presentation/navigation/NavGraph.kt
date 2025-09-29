package com.rure.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rure.knr_takingattendance.data.entities.Position
import com.rure.knr_takingattendance.domain.usecase.activity_date.GetActivitiesFromUseCase
import com.rure.knr_takingattendance.domain.usecase.member.DeleteMemberUseCase
import com.rure.knr_takingattendance.domain.usecase.member.GetAllMembersUseCase
import com.rure.knr_takingattendance.domain.usecase.member.GetMemberByIdUseCase
import com.rure.knr_takingattendance.domain.usecase.member.SaveMemberUseCase
import com.rure.knr_takingattendance.domain.usecase.member.SubscribeMemberFlowUseCase
import com.rure.knr_takingattendance.domain.usecase.member.UpdateMemberUseCase
import com.rure.knr_takingattendance.domain.usecase.participation.SaveMemberParticipationUseCase
import com.rure.knr_takingattendance.presentation.screen.AddMemberScreen
import com.rure.knr_takingattendance.presentation.screen.HomeScreen
import com.rure.knr_takingattendance.presentation.screen.AttendanceHistoryScreen
import com.rure.knr_takingattendance.presentation.screen.MemberListScreen
import com.rure.knr_takingattendance.presentation.screen.OptionScreen
import com.rure.knr_takingattendance.presentation.viewmodels.MemberViewModel
import java.time.LocalDate

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
            AttendanceHistoryScreen(
                memberId = id,
                toAmendScreen = {
                    navController.navigate(Destination.AmendMember.route + "/${id}")
                }
            )
            onScreenChanged(Destination.MemberDetail)
        }

        composable(route = Destination.AddMember.route) {
            AddMemberScreen(
                { navController.popBackStack() }
            )
            onScreenChanged(Destination.AddMember)
        }
        composable(
            route = Destination.AmendMember.route  + "/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
            )
        ) {
            val id = it.arguments?.getInt("id") ?: throw  Exception("No Arguments For id.")
            AddMemberScreen(
                { navController.popBackStack() },
                isAmend = true,
                id = id
            )
            onScreenChanged(Destination.AmendMember)
        }

        composable(route = Destination.MemberList.route) {
            MemberListScreen(
                toDetail = { navController.navigate(Destination.MemberDetail.route + "/$it") }
            )
            onScreenChanged(Destination.MemberList)
        }

        composable(route = Destination.Option.route) {
            OptionScreen(
                toAddMember = { navController.navigate(Destination.AddMember.route) },
                toMemberList = { navController.navigate(Destination.MemberList.route) },
                toSaveAttendance = {  }
            )
            onScreenChanged(Destination.Option)
        }
    }
}