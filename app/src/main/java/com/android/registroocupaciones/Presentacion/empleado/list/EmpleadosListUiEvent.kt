package com.android.registroocupaciones.Presentacion.empleado.list

sealed class EmpleadosListUiEvent {
    object Load: EmpleadosListUiEvent()
    object Refresh: EmpleadosListUiEvent()
    data class Delete(val id: Int): EmpleadosListUiEvent()
    data class ShowMessage(val message: String): EmpleadosListUiEvent()
    object ClearMessage: EmpleadosListUiEvent()
    object CreateNew: EmpleadosListUiEvent()
    data class Edit(val id: Int): EmpleadosListUiEvent()
}
