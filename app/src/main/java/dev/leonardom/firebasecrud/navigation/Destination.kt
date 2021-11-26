package dev.leonardom.firebasecrud.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destination(
    val route: String,
    val arguments: List<NamedNavArgument>
){
    object FacultyList : Destination("facultyList", emptyList())
    object FacultyDetail: Destination(
        route = "facultyDetail",
        arguments = listOf(
            navArgument("facultyId") {
                nullable = true
            }
        )
    )
}
