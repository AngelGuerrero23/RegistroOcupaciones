package com.android.registroocupaciones.Presentacion.ocupaciones.list

sealed class OcupacionesListUiEvent{
    object Load: OcupacionesListUiEvent()
    object Refresh: OcupacionesListUiEvent()
    data class ShowMessage(val message: String): OcupacionesListUiEvent()
    object ClearMessage : OcupacionesListUiEvent()
    object CreateNew: OcupacionesListUiEvent()
    data class Edit(val id: Int): OcupacionesListUiEvent()
}

