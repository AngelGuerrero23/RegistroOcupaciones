package com.android.registroocupaciones.Presentacion.ocupaciones.edit

//FORM
sealed interface OcupacionFormUiEvent{
    data class Load(val id: Int?): OcupacionFormUiEvent
    data class DescripcionChanged(val value: String): OcupacionFormUiEvent
    data class esPuestoDireccionChanged(val value: Boolean) : OcupacionFormUiEvent
    data object Save : OcupacionFormUiEvent
    data object Delete : OcupacionFormUiEvent
}

