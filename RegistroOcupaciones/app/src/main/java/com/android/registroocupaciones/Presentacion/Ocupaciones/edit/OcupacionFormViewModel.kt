package com.android.registroocupaciones.Presentacion.Ocupaciones.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.android.registroocupaciones.domain.model.Ocupacion
import com.android.registroocupaciones.domain.usecase.DeleteOcupacionUseCase
import com.android.registroocupaciones.domain.usecase.GetOcupacionUseCase
import com.android.registroocupaciones.domain.usecase.ObserveOcupacionUseCase
import com.android.registroocupaciones.domain.usecase.UpsertOcupacionUseCase
import com.android.registroocupaciones.domain.usecase.validateDescripcion
import com.android.registroocupaciones.domain.usecase.validateSueldo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.serialization.Serializable

sealed class Routes{
    @Serializable
    data class Detail(val id: Int)
}
@HiltViewModel
class OcupacionFormViewModel @Inject constructor(
    private val getOcupacionUseCase: GetOcupacionUseCase,
    private val upsertOcupacionUseCase: UpsertOcupacionUseCase,
    private val deleteOcupacionUseCase: DeleteOcupacionUseCase,
    private val observeOcupacionUseCase: ObserveOcupacionUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val ocupacionId: Int? = savedStateHandle.get<Int>("id")

    private val _state = MutableStateFlow(OcupacionFormUiState())
    val state: StateFlow<OcupacionFormUiState> = _state.asStateFlow()

    private var ocupacionesExistentes: List<String> = emptyList()

    init {
        loadOcupacion(ocupacionId)
        viewModelScope.launch {
            observeOcupacionUseCase().collect { list ->
                ocupacionesExistentes = list.map { it.Descripcion }
            }
        }
    }

    fun onEvent(event: OcupacionFormUiEvent) {
        when (event) {
            is OcupacionFormUiEvent.Load -> loadOcupacion(event.id)
            is OcupacionFormUiEvent.DescripcionChanged -> _state.update {
                it.copy(descripcion = event.value, descripcionError = null)
            }

            is OcupacionFormUiEvent.SueldoChanged -> _state.update {
                it.copy(sueldo = event.value, sueldoError = null)
            }

            OcupacionFormUiEvent.Save -> onSave()
            OcupacionFormUiEvent.Delete -> onDelete()
        }
    }

    private fun loadOcupacion(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, ocupacionId = null) }
            return
        }
        viewModelScope.launch {
            val ocupacion = getOcupacionUseCase(id)
            if (ocupacion != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        ocupacionId = ocupacion.OcupacionId,
                        descripcion = ocupacion.Descripcion,
                        sueldo = ocupacion.Sueldo.toString()
                    )
                }
            } else {
                _state.update { it.copy(isNew = true, ocupacionId = null) }
            }
        }
    }

    private fun onSave() {
        val descripcion = state.value.descripcion
        val descripcionValidation = validateDescripcion(descripcion, ocupacionesExistentes)
        val sueldoValidation = validateSueldo(state.value.sueldo)

        if (!descripcionValidation.isValid || !sueldoValidation.isValid) {
            _state.update(
                {it.copy(
                    descripcionError = descripcionValidation.error,
                    sueldoError = sueldoValidation.error
                )}
            )

            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val ocupacion = Ocupacion(
                OcupacionId = state.value.ocupacionId ?: 0,
                Descripcion = descripcion,
                Sueldo = state.value.sueldo.toDouble()
            )

            val result = upsertOcupacionUseCase(ocupacion)
            result.onSuccess { newId ->
                _state.update {
                    it.copy(
                        isSaving = false, saved = true, ocupacionId = newId, isNew = false
                    )
                }
            }.onFailure {
                _state.update { it.copy(isSaving = false) }
            }
        }

    }


    private fun onDelete() {
        val id = state.value.ocupacionId ?: return
        viewModelScope.launch {
        _state.update { it.copy(isDeleting = true) }
            deleteOcupacionUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}