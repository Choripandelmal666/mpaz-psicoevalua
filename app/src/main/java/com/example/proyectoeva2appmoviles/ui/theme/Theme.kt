package com.example.proyectoeva2appmoviles.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EsquemaClaro = lightColorScheme(
    primary = Verde,
    onPrimary = Blanco,
    background = Fondo,
    surface = Blanco,
    onBackground = Texto,
    onSurface = Texto
)

@Composable
fun ProyectoEva2AppMovilesTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaClaro,
        typography = Typography,
        content = content
    )
}
