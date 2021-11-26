package dev.leonardom.firebasecrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.leonardom.firebasecrud.navigation.Destination
import dev.leonardom.firebasecrud.presentation.faculty_detail.FacultyDetailScreen
import dev.leonardom.firebasecrud.presentation.faculty_detail.FacultyDetailViewModel
import dev.leonardom.firebasecrud.presentation.faculty_list.FacultyListScreen
import dev.leonardom.firebasecrud.presentation.faculty_list.FacultyListViewModel
import dev.leonardom.firebasecrud.ui.theme.FirebaseCRUDTheme

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseCRUDTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Destination.FacultyList.route,
                ){
                    addFacultyList(navController)

                    addFacultyDetail()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
fun NavGraphBuilder.addFacultyList(
    navController: NavController
){
    composable(
        route = Destination.FacultyList.route
    ){

        val viewModel: FacultyListViewModel = hiltViewModel()
        val state = viewModel.state.value
        val isRefreshing = viewModel.isRefreshing.collectAsState()

        FacultyListScreen(
            state = state,
            navigateToFacultyDetail = {
                navController.navigate(Destination.FacultyDetail.route)
            },
            isRefreshing = isRefreshing.value,
            refreshData = viewModel::getFacultyList,
            onItemClick = { facultyId ->
                navController.navigate(
                    Destination.FacultyDetail.route + "?facultyId=$facultyId"
                )
            }
        )
    }
}

fun NavGraphBuilder.addFacultyDetail() {
    composable(
        route = Destination.FacultyDetail.route + "?facultyId={facultyId}"
    ){

        val viewModel: FacultyDetailViewModel = hiltViewModel()
        val state = viewModel.state.value

        FacultyDetailScreen(
            state = state,
            addNewFaculty = viewModel::addNewFaculty,
            updateFaculty = viewModel::updateFaculty
        )
    }
}