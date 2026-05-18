package com.android.registroocupaciones.Presentacion.empleado.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.registroempleados.domain.usecase.DeleteEmpleadoUseCase
import com.android.registroempleados.domain.usecase.ObserveEmpleadoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmpleadoListViewModel @Inject constructor(
    private val observeEmpleadoUseCase: ObserveEmpleadoUseCase,
    private val deleteEmpleadoUseCase: DeleteEmpleadoUseCase
): ViewModel(){
    private val _state = MutableStateFlow(EmpleadoListUiState(isLoading = true))
    val state: StateFlow<EmpleadoListUiState> = _state.asStateFlow()

    init{
        loadEmpleado()
    }

    fun onEvent(event: EmpleadosListUiEvent){
        when (event){
            EmpleadosListUiEvent.Load-> loadEmpleado()
            EmpleadosListUiEvent.Refresh -> loadEmpleado()
            is EmpleadosListUiEvent.Delete -> onDelete(event.id)
            is EmpleadosListUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            EmpleadosListUiEvent.ClearMessage -> _state.update { it.copy(message = null) }
            EmpleadosListUiEvent.CreateNew ->_state.update { it.copy(navigateToCreate = true) }
            is EmpleadosListUiEvent.Edit ->_state.update { it.copy(navigateToEditId = event.id) }
        }
    }

    fun loadEmpleado(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeEmpleadoUseCase().collectLatest { list-> _state.update { it.copy(
                isLoading = false, empleado = list, message = null) } }
        }
    }

    private fun onDelete(id: Int){
        viewModelScope.launch {
            deleteEmpleadoUseCase(id)
            onEvent(EmpleadosListUiEvent.ShowMessage("Eliminado"))
        }
    }
}
