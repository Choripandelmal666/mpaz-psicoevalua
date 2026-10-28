package com.example.proyectoeva2appmoviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.proyectoeva2appmoviles.data.BaseHelper
import com.example.proyectoeva2appmoviles.ui.AppPsicoEvalua
import com.example.proyectoeva2appmoviles.ui.theme.ProyectoEva2AppMovilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val base = BaseHelper(this)
        setContent {
            ProyectoEva2AppMovilesTheme {
                AppPsicoEvalua(base)
            }
        }
    }
}
