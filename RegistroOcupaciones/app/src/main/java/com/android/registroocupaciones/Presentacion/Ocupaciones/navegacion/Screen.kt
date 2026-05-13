package com.android.registroocupaciones.Presentacion.Ocupaciones.navegacion

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen{
    @Serializable
    data object OcupacionList: Screen()
    @Serializable
    data class OcupacionForm(val ocupacionId: Int =0): Screen()
}