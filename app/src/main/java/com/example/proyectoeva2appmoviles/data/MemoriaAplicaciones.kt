package com.example.proyectoeva2appmoviles.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MemoriaAplicaciones {
    private val lista = mutableListOf<Aplicacion>()
    private var siguienteId = 1L

    fun crear(estudiante: String, curso: String): String? {
        val codigo = (1000..9999).random().toString()
        if (lista.any { it.codigo == codigo }) return null
        lista.add(
            Aplicacion(
                id = siguienteId++,
                codigo = codigo,
                estudiante = estudiante,
                curso = curso,
                instrumento = instrumentoDisponible,
                estado = "pendiente"
            )
        )
        return codigo
    }

    fun listar(): List<Aplicacion> = lista.asReversed()

    fun buscar(codigo: String): Aplicacion? {
        return lista.find { it.codigo == codigo.trim() }
    }

    fun empezar(codigo: String): Boolean {
        return cambiar(codigo, "pendiente") { actual ->
            actual.copy(estado = "en desarrollo", fechaInicio = ahora())
        }
    }

    fun enviar(codigo: String, respuestas: String, puntaje: String): Boolean {
        return cambiar(codigo, "en desarrollo") { actual ->
            actual.copy(
                estado = "finalizada",
                respuestas = respuestas,
                puntaje = puntaje,
                fechaTermino = ahora()
            )
        }
    }

    fun guardarObservacion(codigo: String, observacion: String): Boolean {
        return cambiar(codigo, "finalizada") { actual ->
            actual.copy(estado = "revisada", observacion = observacion)
        }
    }

    private fun cambiar(
        codigo: String,
        estadoEsperado: String,
        cambio: (Aplicacion) -> Aplicacion
    ): Boolean {
        val indice = lista.indexOfFirst { it.codigo == codigo }
        if (indice == -1) return false
        val actual = lista[indice]
        if (actual.estado != estadoEsperado) return false
        lista[indice] = cambio(actual)
        return true
    }

    private fun ahora(): String {
        val formato = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        return formato.format(Date())
    }
}
