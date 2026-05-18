package com.android.registroocupaciones.Presentacion.empleado.edit

import java.time.LocalDate

sealed interface EmpleadoFormUiEvent {
    data class Load(val id:Int?): EmpleadoFormUiEvent
    data class NombresChanged(val value: String): EmpleadoFormUiEvent
    data class fechaChanged(val value: LocalDate): EmpleadoFormUiEvent
    data class sexoChanged(val value: String): EmpleadoFormUiEvent
    data class sueldoChanged(val value: String): EmpleadoFormUiEvent
    data object Save: EmpleadoFormUiEvent
    data object Delete: EmpleadoFormUiEvent
}