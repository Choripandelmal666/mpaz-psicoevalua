package com.example.proyectoeva2appmoviles.data

data class Aplicacion(
    val id: Long = 0,
    val codigo: String,
    val estudiante: String,
    val curso: String,
    val instrumento: String,
    val estado: String,
    val respuestas: String = "",
    val fechaInicio: String = "",
    val fechaTermino: String = "",
    val puntaje: String = "",
    val observacion: String = ""
)
