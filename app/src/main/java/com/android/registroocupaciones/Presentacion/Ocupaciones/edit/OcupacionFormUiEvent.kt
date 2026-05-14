package com.android.registroocupaciones.Presentacion.Ocupaciones.edit

//FORM
sealed interface OcupacionFormUiEvent{
    data class Load(val id: Int?): OcupacionFormUiEvent
    data class DescripcionChanged(val value: String): OcupacionFormUiEvent
    data class SueldoChanged(val value: String) : OcupacionFormUiEvent
    data object Save : OcupacionFormUiEvent
    data object Delete : OcupacionFormUiEvent
}

