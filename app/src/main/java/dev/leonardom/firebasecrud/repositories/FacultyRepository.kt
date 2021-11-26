package dev.leonardom.firebasecrud.repositories

import com.google.firebase.firestore.CollectionReference
import dev.leonardom.firebasecrud.model.Faculty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FacultyRepository
@Inject
constructor(
    private val facultyList: CollectionReference
)
{


    fun addNewFaculty(faculty: Faculty) {
        try {
            facultyList.document(faculty.id).set(faculty)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getFacultyList() : Flow<ResultRepository<List<Faculty>>> = flow {
        try {
            emit(ResultRepository.Loading<List<Faculty>>())

            val facultyList = facultyList.get().await().map{ document ->
                document.toObject(Faculty:: class.java)
            }

            emit((ResultRepository.Success<List<Faculty>>(data = facultyList)))

        } catch (e: Exception) {
            emit(ResultRepository.Error<List<Faculty>>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }

    fun getFacultyById(facultyId: String): Flow<ResultRepository<Faculty>> = flow {
        try {
            emit(ResultRepository.Loading<Faculty>())

            val faculty = facultyList.whereGreaterThanOrEqualTo("id", facultyId)
                .get().await().toObjects(Faculty::class.java).first()

            emit((ResultRepository.Success<Faculty>(data = faculty)))

        } catch (e: Exception) {
            emit(ResultRepository.Error<Faculty>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }

    fun updateFaculty(facultyId: String, faculty: Faculty) {
        try {
            val map = mapOf(
                "name" to faculty.name,
                "status" to faculty.status
            )

            facultyList.document(facultyId).update(map)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}