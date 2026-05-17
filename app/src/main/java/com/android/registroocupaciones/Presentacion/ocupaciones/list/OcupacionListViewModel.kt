package com.android.registroocupaciones.Presentacion.ocupaciones.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.registroocupaciones.domain.ocupacion.usecase.DeleteOcupacionUseCase
import com.android.registroocupaciones.domain.ocupacion.usecase.ObserveOcupacionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OcupacionListViewModel @Inject constructor(
    private val observeOcupacionUseCase: ObserveOcupacionUseCase,
    private val deleteOcupacionUseCase: DeleteOcupacionUseCase
): ViewModel() {
    private val _state = MutableStateFlow(OcupacionListUiState(isLoading = true))
    val state: StateFlow<OcupacionListUiState> = _state.asStateFlow()

    init {
        loadOcupacion()
    }

    fun onEvent(event: OcupacionesListUiEvent) {
        when (event) {
            OcupacionesListUiEvent.Load -> loadOcupacion()
            OcupacionesListUiEvent.Refresh -> loadOcupacion()
            is OcupacionesListUiEvent.Delete -> onDelete(event.id)
            is OcupacionesListUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            OcupacionesListUiEvent.ClearMessage ->_state.update { it.copy(message = null) }
            OcupacionesListUiEvent.CreateNew ->_state.update { it.copy(navigateToCreate = true) }
            is OcupacionesListUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }

        }
    }

    fun loadOcupacion(){
        viewModelScope.launch {
            _state.update {it.copy(isLoading = true) }
            observeOcupacionUseCase().collectLatest { list->_state.update { it.copy(
                isLoading = false, ocupacion = list, message = null) }}
        }
    }
    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteOcupacionUseCase(id)
            onEvent(OcupacionesListUiEvent.ShowMessage("Eliminado"))
        }

    }
}