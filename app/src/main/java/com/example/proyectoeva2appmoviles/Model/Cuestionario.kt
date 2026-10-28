package com.example.proyectoeva2appmoviles.Model

class Cuestionario (codigo: String, nombreInstrumento: String, tipoInstrumento: tipoInstrumento, var InstrumentoNoDisponible: Boolean, var InstrumentoDisponible:Boolean, ):
    Instrumento(codigo, nombreInstrumento, tipoInstrumento) {

    var p1 = 0
    var p2 = 0
    var p3 = 0
    var p4 = 0


    fun MenuCuestionario() {
        println("Bienvenido al cuestionario de Instrumentos Digitales")
        println("Responda las siguientes preguntas escribiendo el numero de la opcion correspondiente")

        var opc = 0
        println("Pregunta 1 .- ¿Organizas tus útiles antes de empezar a estudiar?")//p1
        println("1 - Casi siempre")
        println("2 - A veces")
        println("3 - Casi nunca")
        opc = readln().toInt()
        when (opc) {
            1 -> {
                p1 = 2
            }

            2 -> {
                p1 = 1
            }

            3 -> {
                p1 = 0
            }

            else -> {
                println("Opcion no valida")
            }
        }
        println("Pregunta 2 .- ¿Terminas las tareas el día en que te las piden?")//p2
        println("1 - Casi siempre")
        println("2 - A veces")
        println("3 - Casi nunca")
        opc = readln().toInt()
        when (opc) {
            1 -> {
                p2 = 2
            }

            2 -> {
                p2 = 1
            }

            3 -> {
                p2 = 0
            }

            else -> {
                println("Opcion no valida")
            }
        }

        println("Pregunta 3 .- ¿Estudias en un lugar tranquilo?")//p2
        println("1 - Casi siempre")
        println("2 - A veces")
        println("3 - Casi nunca")
        opc = readln().toInt()
        when (opc) {
            1 -> {
                p3 = 2
            }

            2 -> {
                p3 = 1
            }

            3 -> {
                p3 = 0
            }

            else -> {
                println("Opcion no valida")
            }
        }

        println("Pregunta 4 .- ¿Repasas lo visto en clases?")//p2
        println("1 - Casi siempre")
        println("2 - A veces")
        println("3 - Casi nunca")
        opc = readln().toInt()
        when (opc) {
            1 -> {
                p4 = 2
            }

            2 -> {
                p4 = 1
            }

            3 -> {
                p4 = 0
            }

            else -> {
                println("Opcion no valida")
            }
        }
    }
}
