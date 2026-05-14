package com.android.registroocupaciones.Presentacion.Ocupaciones.list

import com.android.registroocupaciones.domain.model.Ocupacion

data class OcupacionListUiState(
    val isLoading: Boolean = false,
    val ocupacion: List<Ocupacion> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId:Int?=null,
    val error: String? = null
)

