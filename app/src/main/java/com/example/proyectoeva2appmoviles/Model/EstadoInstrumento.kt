package com.example.proyectoeva2appmoviles.Model

sealed class EstadoInstrumento {
    data class Disponible (val instrumento: Instrumento?): EstadoInstrumento()
    data class NoDisponible (val instrumento: Instrumento?): EstadoInstrumento()
    object Pendiente: EstadoInstrumento()

    fun manejoEstadoInstrumento(estado:EstadoInstrumento) {
        when(estado) {
        is Disponible ->    println("El instrumento esta libre para el estudiante")
        is NoDisponible ->    println("Un estudiante se encuentra usando el instrumento actualmente")
        is Pendiente ->    println("En Proceso de asignacion del instrumento!")
        }
    }
}