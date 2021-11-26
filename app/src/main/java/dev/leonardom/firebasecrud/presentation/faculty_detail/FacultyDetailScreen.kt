package dev.leonardom.firebasecrud.presentation.faculty_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.leonardom.firebasecrud.ui.theme.Red100

@Composable
fun FacultyDetailScreen(
    state: FacultyDetailState,
    addNewFaculty: (String, String) -> Unit,
    updateFaculty: (String, String) -> Unit
) {

    var name by remember(state.faculty?.name){ mutableStateOf(state.faculty?.name ?: "") }
    var status by remember(state.faculty?.status) { mutableStateOf(state.faculty?.status ?: "") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = name,
                onValueChange = { name = it},
                label = {
                    Text(text = "Name")
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = status,
                onValueChange = { status = it},
                label = {
                    Text(text = "Status")
                }
            )
        }

        if(state.error.isNotBlank()) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                text = state.error,
                style = TextStyle(
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            )
        }

        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            if (state.faculty?.id != null){
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    onClick = {
                        updateFaculty(name, status)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Red100
                    )
                ) {
                    Text(
                        text = "Update Faculty",
                        color = Color.White
                    )
                }
            } else {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    onClick = {
                        addNewFaculty(name, status)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Red100
                    )
                ) {
                    Text(
                        text = "Add New Faculty",
                        color = Color.White
                    )
                }
            }
        }
    }
}