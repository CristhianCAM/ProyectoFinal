package dev.leonardom.firebasecrud.presentation.faculty_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.leonardom.firebasecrud.repositories.FacultyRepository
import dev.leonardom.firebasecrud.repositories.ResultRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FacultyListViewModel
@Inject
constructor(
    private val facultyRepository: FacultyRepository
) : ViewModel(){

    private val _state: MutableState<FacultyListState> = mutableStateOf(FacultyListState())
    val state: State<FacultyListState> = _state

    private val _isRefreshing  = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getFacultyList()
    }

    fun getFacultyList() {
        facultyRepository.getFacultyList().onEach { result ->
            when(result) {
                is ResultRepository.Error -> {
                    _state.value = FacultyListState(error = result.message ?: "Error Inesperado")
                }
                is ResultRepository.Loading -> {
                    _state.value = FacultyListState(isLoading = true)
                }
                is ResultRepository.Success -> {
                    _state.value = FacultyListState(faculties = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}