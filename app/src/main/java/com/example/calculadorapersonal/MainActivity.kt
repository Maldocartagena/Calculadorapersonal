package com.example.calculadorapersonal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.calculadorapersonal.ui.Calculadora
import com.example.calculadorapersonal.ui.theme.CalculadorapersonalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aquí aplicamos el tema general de la app
            CalculadorapersonalTheme {
                // Llamamos a la pantalla principal que está en ui/Calculadora.kt
                Calculadora()
            }
        }
    }
}
