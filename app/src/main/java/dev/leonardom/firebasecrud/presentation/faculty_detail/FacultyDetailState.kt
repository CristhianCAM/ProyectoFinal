package dev.leonardom.firebasecrud.presentation.faculty_detail

import dev.leonardom.firebasecrud.model.Faculty

data class FacultyDetailState(
    val isLoading: Boolean = false,
    val faculty: Faculty? = null,
    val error: String = ""
)
