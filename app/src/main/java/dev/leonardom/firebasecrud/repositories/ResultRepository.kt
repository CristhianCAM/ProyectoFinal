package dev.leonardom.firebasecrud.repositories

sealed class ResultRepository<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T?) : ResultRepository<T>(data)

    class Error<T>(message: String?, data: T? = null) : ResultRepository<T>(data, message)

    class Loading<T>(data: T? = null) : ResultRepository<T>(data)
}
