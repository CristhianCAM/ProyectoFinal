package dev.leonardom.firebasecrud.presentation.faculty_list

import dev.leonardom.firebasecrud.model.Faculty

data class FacultyListState(
    val isLoading: Boolean = false,
    val faculties: List<Faculty> = emptyList(),
    val error: String = ""
)
