package com.android.registroocupaciones.Presentacion.horaextra.list

import com.android.registroempleados.domain.model.Empleados
import com.android.registroocupaciones.domain.horaextra.model.HoraExtra
data class HoraExtraListUiState(
    val horasExtra: List<HoraExtra> = emptyList(),
    val empleados: List<Empleados> = emptyList(),
    val isLoading: Boolean = false,
    val navigateToCreate: Boolean = false,
    val message: String? = null,
    val error: String? = null,
    val navigateToEdit: Int? = null
)