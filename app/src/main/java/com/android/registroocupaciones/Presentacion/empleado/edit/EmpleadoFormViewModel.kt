package com.android.registroocupaciones.Presentacion.empleado.edit

import androidx.compose.ui.graphics.findFirstRoot
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.android.registroempleados.domain.model.Empleados
import com.android.registroempleados.domain.usecase.DeleteEmpleadoUseCase
import com.android.registroempleados.domain.usecase.GetEmpleadoUseCase
import com.android.registroempleados.domain.usecase.ObserveEmpleadoUseCase
import com.android.registroempleados.domain.usecase.UpsertEmpleadoUseCase
import com.android.registroempleados.domain.usecase.validateFecha
import com.android.registroempleados.domain.usecase.validateNombres
import com.android.registroempleados.domain.usecase.validateSexo
import com.android.registroempleados.domain.usecase.validateSueldo
import com.android.registroocupaciones.Presentacion.navegacion.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EmpleadoFormViewModel @Inject constructor(
    private val getEmpleadoUseCase: GetEmpleadoUseCase,
    private val upsertEmpleadoUseCase: UpsertEmpleadoUseCase,
    private val deleteEmpleadoUseCase: DeleteEmpleadoUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val empleadoId = savedStateHandle.toRoute<Screen.EmpleadoForm>().empleadoId

    private val _state = MutableStateFlow(EmpleadoFormUiState())
    val state: StateFlow<EmpleadoFormUiState> = _state.asStateFlow()

    init {
        loadEmpleados(empleadoId)
    }

    fun onEvent(event: EmpleadoFormUiEvent) {
        when (event) {
            is EmpleadoFormUiEvent.Load -> loadEmpleados(event.id)
            is EmpleadoFormUiEvent.NombresChanged -> _state.update {
                it.copy(
                    nombres = event.value,
                    nombresError = null
                )
            }

            is EmpleadoFormUiEvent.fechaChanged -> _state.update {
                it.copy(
                    fechaIngreso = event.value,
                    fechaIngresoError = null
                )
            }

            is EmpleadoFormUiEvent.sexoChanged -> _state.update {
                it.copy(
                    sexo = event.value,
                    sexoError = null
                )
            }

            is EmpleadoFormUiEvent.sueldoChanged -> _state.update {
                it.copy(
                    sueldo = event.value,
                    sueldoError = null
                )
            }
            EmpleadoFormUiEvent.Save -> onSave()
            EmpleadoFormUiEvent.Delete -> onDelete()
        }
    }


    private fun loadEmpleados(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, empleadoId = null) }
            return
        }
        viewModelScope.launch {
            val empleado = getEmpleadoUseCase(id)
            if(empleado !=null){
                _state.update {
                    it.copy(
                        isNew = false,
                        empleadoId = empleado.empleadosId,
                        nombres = empleado.nombres,
                        fechaIngreso = empleado.fechaIngreso,
                        sexo = empleado.sexo,
                        sueldo = empleado.sueldo.toString()
                    )
                }
            }else {
                _state.update { it.copy(
                    isNew = true,
                    empleadoId = null
                ) }
            }
        }
    }

    private fun onSave(){0
        val nombres = state.value.nombres
        val nombresValidate = validateNombres(nombres, emptyList())
        val sexo = state.value.sexo
        val sexoValidate = validateSexo(sexo)
        val fechaIngreso = state.value.fechaIngreso
        val fechaValidate = validateFecha(state.value.fechaIngreso)
        val sueldo = state.value.sueldo
        val sueldoValidate = validateSueldo(state.value.sueldo)

        if(!nombresValidate.isValid || !sexoValidate.isValid || !sueldoValidate.isValid || !fechaValidate.isValid )
        {
            _state.update {
                it.copy(
                    nombresError = nombresValidate.error,
                    sueldoError =  sueldoValidate.error,
                    fechaIngresoError = fechaValidate.error,
                    sexoError = sexoValidate.error
                )
            }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val empleado = Empleados(
                empleadosId = state.value.empleadoId?:0,
                nombres = nombres,
                fechaIngreso = fechaIngreso,
                sexo = sexo,
                sueldo = sueldo.toDoubleOrNull()?:0.0
            )

            val result = upsertEmpleadoUseCase(empleado)
            result.onSuccess { newId->
                _state.update { it.copy(
                    isSaving = false,
                    saved = true,
                    empleadoId = newId,
                    isNew = false) }
            }.onFailure {
                _state.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun onDelete(){
        val id= state.value.empleadoId?:return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteEmpleadoUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}