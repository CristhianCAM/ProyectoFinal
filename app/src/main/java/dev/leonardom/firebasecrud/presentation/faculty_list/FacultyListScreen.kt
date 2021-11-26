package dev.leonardom.firebasecrud.presentation.faculty_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.leonardom.firebasecrud.navigation.Destination
import dev.leonardom.firebasecrud.presentation.faculty_list.components.FacultyList
import dev.leonardom.firebasecrud.ui.theme.Red100

@ExperimentalMaterialApi
@Composable
fun FacultyListScreen(
    state: FacultyListState,
    navigateToFacultyDetail: () -> Unit,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    onItemClick: (String) ->Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToFacultyDetail,
                backgroundColor = Red100,
                contentColor = Color.White
            ) {
            }
        }
    ) {
        FacultyList(
            state = state,
            isRefreshing = isRefreshing,
            refreshData = refreshData,
            onItemClick = onItemClick
        )
    }
}