package com.example.proyectoeva2appmoviles.data

data class Opcion(val texto: String, val puntos: Int)

data class Pregunta(val texto: String, val opciones: List<Opcion>)

val instrumentoDisponible = "Hábitos de estudio (simulado)"

val preguntasHabitos = listOf(
    Pregunta(
        texto = "¿Organizas tus útiles antes de empezar a estudiar?",
        opciones = listOf(
            Opcion("Casi nunca", 0),
            Opcion("A veces", 1),
            Opcion("Casi siempre", 2)
        )
    ),
    Pregunta(
        texto = "¿Terminas las tareas el día en que te las piden?",
        opciones = listOf(
            Opcion("Casi nunca", 0),
            Opcion("A veces", 1),
            Opcion("Casi siempre", 2)
        )
    ),
    Pregunta(
        texto = "¿Estudias en un lugar tranquilo?",
        opciones = listOf(
            Opcion("Casi nunca", 0),
            Opcion("A veces", 1),
            Opcion("Casi siempre", 2)
        )
    ),
    Pregunta(
        texto = "¿Repasas lo visto en clases?",
        opciones = listOf(
            Opcion("Casi nunca", 0),
            Opcion("A veces", 1),
            Opcion("Casi siempre", 2)
        )
    )
)
