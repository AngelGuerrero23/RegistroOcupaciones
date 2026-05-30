package com.android.registroocupaciones.Presentacion.empleado.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.android.registroempleados.domain.model.Empleados
import com.android.registroempleados.domain.usecase.DeleteEmpleadoUseCase
import com.android.registroempleados.domain.usecase.GetEmpleadoUseCase
import com.android.registroempleados.domain.usecase.UpsertEmpleadoUseCase
import com.android.registroempleados.domain.usecase.validateFecha
import com.android.registroempleados.domain.usecase.validateFrecuenciaPago
import com.android.registroempleados.domain.usecase.validateNombres
import com.android.registroempleados.domain.usecase.validateOcupacionId
import com.android.registroempleados.domain.usecase.validateSexo
import com.android.registroempleados.domain.usecase.validateSueldo
import com.android.registroocupaciones.Presentacion.navegacion.Screen
import com.android.registroocupaciones.domain.empleado.repository.EmpleadosRepository
import com.android.registroocupaciones.domain.ocupacion.usecase.ObserveOcupacionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmpleadoFormViewModel @Inject constructor(
    private val repository: EmpleadosRepository,
    private val getEmpleadoUseCase: GetEmpleadoUseCase,
    private val upsertEmpleadoUseCase: UpsertEmpleadoUseCase,
    private val deleteEmpleadoUseCase: DeleteEmpleadoUseCase,
    private val observeOcupacionUseCase: ObserveOcupacionUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val empleadoId = savedStateHandle.toRoute<Screen.EmpleadoForm>().empleadoId

    private val _state = MutableStateFlow(EmpleadoFormUiState())
    val state: StateFlow<EmpleadoFormUiState> = _state.asStateFlow()

    init {
        loadOcupacion()
    }

    fun onEvent(event: EmpleadoFormUiEvent) {
        when (event) {
            is EmpleadoFormUiEvent.Load -> loadEmpleados(event.id)
            is EmpleadoFormUiEvent.OcupacionChanged -> _state.update { it.copy(ocupacionId = event.value, ocupacionError = null) }
            is EmpleadoFormUiEvent.DescripcionOcupacionChanged -> _state.update { it.copy(descripcionOcupacion = event.value) }
            is EmpleadoFormUiEvent.FechaIngresoChanged -> _state.update { it.copy(fechaIngreso = event.value, fechaIngresoError = null) }
            is EmpleadoFormUiEvent.NombresChanged -> _state.update { it.copy(nombres = event.value, nombresError = null) }
            is EmpleadoFormUiEvent.SexoChanged -> _state.update { it.copy(sexo = event.value, sexoError = null) }
            is EmpleadoFormUiEvent.SueldoChanged -> _state.update { it.copy(sueldo = event.value, sueldoError = null) }
            is EmpleadoFormUiEvent.FrecuenciaPagoChanged -> _state.update { it.copy(frecuenciaPago = event.value, sueldoError = null) }
            EmpleadoFormUiEvent.Save -> onSave()
            EmpleadoFormUiEvent.Delete -> onDelete()
        }
    }

    private fun loadOcupacion(){
        viewModelScope.launch {
            observeOcupacionUseCase().collectLatest { list ->
                _state.update { it.copy(ocupaciones = list) }
            }
        }
    }

    fun loadEmpleados(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, empleadoId = null) }
            return
        }
        viewModelScope.launch {
            val empleado = getEmpleadoUseCase(id)
            val ocupaciones = observeOcupacionUseCase().first()
            if(empleado != null){
                _state.update {
                    it.copy(
                        isNew = false,
                        empleadoId = empleado.empleadosId,
                        ocupacionId = empleado.ocupacionId.toString(),
                        descripcionOcupacion = (ocupaciones.find { it.OcupacionId == empleado.ocupacionId })?.Descripcion ?: "",
                        nombres = empleado.nombres,
                        sexo = empleado.sexo,
                        fechaIngreso = empleado.fechaIngreso,
                        frecuenciaPago = empleado.frecuenciaPago
                    )
                }
            } else {
                _state.update { it.copy(
                    isNew = true,
                    empleadoId = null
                ) }
            }
        }
    }

    private fun onSave(){
        val ocupacionId = state.value.ocupacionId.toIntOrNull()?:0
        val fechaIngreso = state.value.fechaIngreso
        val nombres = state.value.nombres
        val sexo = state.value.sexo
        val sueldoText = state.value.sueldo
        val frecuenciaPago = state.value.frecuenciaPago

        val ocupacionValidation = validateOcupacionId(ocupacionId)
        val fechaIngresoValidation = validateFecha(fechaIngreso)
        val nombresValidation = validateNombres(nombres)
        val sexoValidation = validateSexo(sexo)
        val sueldoValidation = validateSueldo(sueldoText)
        val frecuenciaPagoValidation = validateFrecuenciaPago(frecuenciaPago.descripcion)

        if(!nombresValidation.isValid || !sexoValidation.isValid || !sueldoValidation.isValid || !fechaIngresoValidation.isValid) {
            _state.update {
                it.copy(
                    ocupacionError = ocupacionValidation.error,
                    fechaIngresoError = fechaIngresoValidation.error,
                    nombresError = nombresValidation.error,
                    sexoError = sexoValidation.error,
                    sueldoError = sueldoValidation.error,
                    frecuenciaPagoError = frecuenciaPagoValidation.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val empleado = Empleados(
                empleadosId = state.value.empleadoId ?: 0,
                ocupacionId = ocupacionId.toInt(),
                nombres = nombres,
                sexo = sexo,
                fechaIngreso = fechaIngreso,
                sueldo = sueldoText.toDouble(),
                frecuenciaPago = frecuenciaPago
            )

            val result = upsertEmpleadoUseCase(empleado)
            result.onSuccess { newId ->
                _state.update { it.copy(
                    isSaving = false,
                    saved = true,
                    empleadoId = newId,
                    isNew = false
                ) }
            }.onFailure {
                _state.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun onDelete(){
        val id = state.value.empleadoId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteEmpleadoUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}