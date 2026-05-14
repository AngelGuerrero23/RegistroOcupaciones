package com.android.registroocupaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.android.registroocupaciones.Presentacion.Ocupaciones.navegacion.OcupacionNavHost
import com.android.registroocupaciones.ui.theme.RegistroOcupacionesTheme
import dagger.hilt.android.AndroidEntryPoint

import com.android.registroocupaciones.Presentacion.Ocupaciones.navegacion.OcupacionNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroOcupacionesTheme {
                OcupacionNavHost()
            }
        }
    }
}