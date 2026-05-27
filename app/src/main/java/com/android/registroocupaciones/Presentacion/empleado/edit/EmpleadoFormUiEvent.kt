package com.android.registroocupaciones.Presentacion.empleado.edit

import com.android.registroocupaciones.domain.horaextra.model.FrecuenciaPago
import java.time.LocalDate

sealed interface EmpleadoFormUiEvent {
    data class Load(val id:Int?): EmpleadoFormUiEvent
    data class FechaIngresoChanged(val value: LocalDate) : EmpleadoFormUiEvent
    data class NombresChanged(val value: String): EmpleadoFormUiEvent
    data class SexoChanged(val value: String): EmpleadoFormUiEvent
    data class SueldoChanged(val value: String): EmpleadoFormUiEvent
    data class OcupacionChanged(val value: String): EmpleadoFormUiEvent
    data class DescripcionOcupacionChanged(val value: String): EmpleadoFormUiEvent
    data class FrecuenciaPagoChanged(val value: FrecuenciaPago): EmpleadoFormUiEvent
    data object Save: EmpleadoFormUiEvent
    data object Delete: EmpleadoFormUiEvent
}