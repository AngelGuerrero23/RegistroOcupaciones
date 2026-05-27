package com.android.registroocupaciones.domain.ocupacion.model

data class Ocupacion(
    val OcupacionId:Int = 0,
    val Descripcion: String = "",
    val esPuestoDireccion : Boolean = false
)
