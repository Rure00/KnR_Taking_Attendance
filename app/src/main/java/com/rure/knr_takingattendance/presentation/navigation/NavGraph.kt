package com.rure.knr_takingattendance.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
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
            AttendanceHistoryScreen(id)
            onScreenChanged(Destination.MemberDetail)
        }

        composable(route = Destination.AddMember.route) {
            AddMemberScreen(
                { navController.popBackStack() }
            )
            onScreenChanged(Destination.AddMember)
        }
        composable(
            route = Destination.AmendMember.route  + "/{name}"  + "/{birth}"  + "/{phoneNumber}"  + "/{position}"  + "/{joinDate}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("birth") { type = NavType.StringType },
                navArgument("phoneNumber") { type = NavType.StringType },
                navArgument("position") { type = NavType.StringType },
                navArgument("joinDate") { type = NavType.StringType },
            )
        ) {
            val nameArg = it.arguments?.getString("name") ?: throw  Exception("No Arguments For name.")
            val birthArg = it.arguments?.getString("birth").let { str ->
                LocalDate.parse(str)
            } ?: throw  Exception("No Arguments For birth.")
            val numberArg = it.arguments?.getString("phoneNumber") ?: throw  Exception("No Arguments For phoneNumber.")
            val positionArg = it.arguments?.getString("position").let { str ->
                val typeToken = object: TypeToken<Map<Position, Boolean>>() { } .type
                Gson().fromJson<Map<Position, Boolean>>(str, typeToken)
            } ?: throw  Exception("No Arguments For position.")
            val joinDateArg = it.arguments?.getString("joinDate").let { str ->
                LocalDate.parse(str)
            } ?: throw  Exception("No Arguments For joinDate.")

            AddMemberScreen(
                { navController.popBackStack() },
                name = nameArg,
                birth = birthArg,
                phoneNumber = numberArg,
                position = positionArg,
                joinDate = joinDateArg,
            )
            onScreenChanged(Destination.AmendMember)
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