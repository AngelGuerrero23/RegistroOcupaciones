package com.android.registroocupaciones.Presentacion.empleado.list

import com.android.registroempleados.domain.model.Empleados
import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion

data class EmpleadoListUiState (
    val isLoading: Boolean = false,
    val empleado: List<Empleados> = emptyList(),
    val ocupaciones: List<Ocupacion> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int?=null,
    val error: String? = null
)