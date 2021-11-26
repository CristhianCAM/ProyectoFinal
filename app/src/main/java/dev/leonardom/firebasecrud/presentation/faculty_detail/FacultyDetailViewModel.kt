package dev.leonardom.firebasecrud.presentation.faculty_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.leonardom.firebasecrud.model.Faculty
import dev.leonardom.firebasecrud.repositories.FacultyRepository
import dev.leonardom.firebasecrud.repositories.ResultRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FacultyDetailViewModel
@Inject
constructor(
    private val facultyRepository: FacultyRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state: MutableState<FacultyDetailState> = mutableStateOf(FacultyDetailState())
    val state: State<FacultyDetailState>
        get() = _state

    init {
        savedStateHandle.get<String>("facultyId")?.let { facultyId ->
            getFaculty(facultyId)
        }
    }

    fun addNewFaculty(name: String, status: String) {
        val faculty = Faculty(
            id = UUID.randomUUID().toString(),
            name = name,
            status = status
        )
        facultyRepository.addNewFaculty(faculty)
    }

    private fun getFaculty(facultyId: String) {
        facultyRepository.getFacultyById(facultyId).onEach { result ->
            when(result) {
                is ResultRepository.Error -> {
                    _state.value = FacultyDetailState(error = result.message ?: "Unexpected error")
                }
                is ResultRepository.Loading -> {
                    _state.value = FacultyDetailState(isLoading = true)
                }
                is ResultRepository.Success -> {
                    _state.value = FacultyDetailState(faculty = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateFaculty(newName: String, newStatus: String) {
        if (state.value.faculty == null) {
            _state.value = FacultyDetailState(error = "Unexpected error")
            return
        }

        val facultyEdited = state.value.faculty!!.copy(
            name = newName,
            status = newStatus
        )

        facultyRepository.updateFaculty(facultyEdited.id, facultyEdited)

    }
}