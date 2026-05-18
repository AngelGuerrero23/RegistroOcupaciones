package com.android.registroocupaciones.Presentacion.navegacion

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen{
    @Serializable
    data object OcupacionList: Screen()
    @Serializable
    data class OcupacionForm(val ocupacionId: Int =0): Screen()

    @Serializable
    data class EmpleadoForm(val empleadoId: Int = 0): Screen()
    @Serializable
    data object EmpleadoList: Screen()
}