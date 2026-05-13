package com.android.registroocupaciones.Presentacion.Ocupaciones.list

sealed class OcupacionesListUiEvent{
    object Load: OcupacionesListUiEvent()
    object Refresh: OcupacionesListUiEvent()
    data class Delete(val id:Int): OcupacionesListUiEvent()
    data class ShowMessage(val message: String): OcupacionesListUiEvent()
    object ClearMessage : OcupacionesListUiEvent()
    object CreateNew: OcupacionesListUiEvent()
    data class Edit(val id: Int): OcupacionesListUiEvent()
}

