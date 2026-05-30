package com.android.registroocupaciones.Presentacion.horaextra.edit

import com.android.registroempleados.domain.model.Empleados
import com.android.registroocupaciones.domain.horaextra.model.TipoHoraExtra
import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import java.time.LocalDate

data class HoraExtraFormUiState (
    val horaExtraId: Int? = null,
    val empleadoId: String = "",
    val nombreEmpleado: String = "",
    val fecha: LocalDate = LocalDate.now(),
    val cantidadHoras: String = "",
    val tipo: TipoHoraExtra = TipoHoraExtra.Diurna,
    val empleados: List<Empleados> = emptyList(),
    val ocupaciones: List<Ocupacion> = emptyList(),
    val empleadoError: String? = null,
    val fechaError: String? = null,
    val cantidadHorasError: String? = null,
    val tipoError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)