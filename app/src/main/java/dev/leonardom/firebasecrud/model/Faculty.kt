package dev.leonardom.firebasecrud.model

data class Faculty(
    val id: String,
    val name: String,
    val status: String,
){

    constructor() : this("", "", "")
}
