package com.android.registroocupaciones.Presentacion.horaextra.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.android.registroempleados.domain.usecase.ObserveEmpleadoUseCase
import com.android.registroempleados.domain.usecase.validateFecha
import com.android.registroocupaciones.Presentacion.navegacion.Screen
import com.android.registroocupaciones.domain.horaextra.model.HoraExtra
import com.android.registroocupaciones.domain.horaextra.repository.HoraExtraRepository
import com.android.registroocupaciones.domain.horaextra.usecase.DeleteHoraExtraUseCase
import com.android.registroocupaciones.domain.horaextra.usecase.GetHoraExtraUseCase
import com.android.registroocupaciones.domain.horaextra.usecase.UpsertHoraExtraUseCase
import com.android.registroocupaciones.domain.horaextra.usecase.calcularMontoHoraExtra
import com.android.registroocupaciones.domain.horaextra.usecase.validateCantidadHoras
import com.android.registroocupaciones.domain.horaextra.usecase.validateEmpleadoId
import com.android.registroocupaciones.domain.horaextra.usecase.validateTipoHoraExtra
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
class HoraExtraFormViewModel @Inject constructor(
    private val repository: HoraExtraRepository,
    private val getHoraExtraUseCase: GetHoraExtraUseCase,
    private val upsertHoraExtraUseCase: UpsertHoraExtraUseCase,
    private val deleteHoraExtraUseCase: DeleteHoraExtraUseCase,
    private val observeEmpleadoUseCase: ObserveEmpleadoUseCase,
    private val observeOcupacionUseCase: ObserveOcupacionUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val horaExtraId = savedStateHandle.toRoute<Screen.HoraExtraForm>().horaExtraId

    private val _state = MutableStateFlow(HoraExtraFormUiState())
    val state: StateFlow<HoraExtraFormUiState> = _state.asStateFlow()

    init {
        loadEmpleados()
    }

    fun onEvent(event: HoraExtraFormUiEvent){
        when(event){
            is HoraExtraFormUiEvent.Load -> loadHoraExtra(event.id)
            is HoraExtraFormUiEvent.EmpleadoChanged -> _state.update { it.copy(empleadoId = event.value, empleadoError = null) }
            is HoraExtraFormUiEvent.NombreEmpleadoChanged -> _state.update { it.copy(nombreEmpleado = event.value) }
            is HoraExtraFormUiEvent.FechaChanged -> _state.update { it.copy(fecha = event.value, fechaError = null) }
            is HoraExtraFormUiEvent.CantidadHorasChanged -> _state.update { it.copy(cantidadHoras = event.value, cantidadHorasError = null) }
            is HoraExtraFormUiEvent.TipoChanged -> _state.update { it.copy(tipo = event.value, tipoError = null) }
            HoraExtraFormUiEvent.Save -> onSave()
            HoraExtraFormUiEvent.Delete -> onDelete()
        }
    }

    fun loadEmpleados(){
        viewModelScope.launch {
            observeEmpleadoUseCase().collectLatest { list -> _state.update { it.copy(empleados = list) } }
        }
    }

     fun loadHoraExtra(id: Int){
         if(id == 0 ){
             val empleados = _state.value.empleados
             val ocupaciones = _state.value.ocupaciones
             _state.value = HoraExtraFormUiState(
                 empleados = empleados,
                 ocupaciones = ocupaciones
             )
             return
         }


        viewModelScope.launch {
            val horaExtra = getHoraExtraUseCase(id)
            val empleados = observeEmpleadoUseCase().first()

            if(horaExtra != null){
                _state.update {
                    it.copy(
                        isNew = false,
                        horaExtraId = horaExtra.horaExtraId,
                        empleadoId = horaExtra.empleadoId.toString(),
                        nombreEmpleado = (empleados.find { it.empleadosId == horaExtra.empleadoId })?.nombres ?: "",
                        fecha = horaExtra.fecha,
                        cantidadHoras = horaExtra.cantidadHoras.toString(),
                        tipo = horaExtra.tipoHoraExtra
                    )
                }
            }else{
                _state.update { it.copy(isNew = true, horaExtraId = null) }
            }
        }
    }

    private fun onSave(){
        val empleadoId = state.value.empleadoId?.toIntOrNull() ?: 0
        val fecha = state.value.fecha
        val cantidadHoras = state.value.cantidadHoras.toIntOrNull()?:0
        val tipo = state.value.tipo

        val empleadoValidation = validateEmpleadoId(empleadoId)
        val fechaValidation = validateFecha(fecha)
        val cantidadHorasValidation = validateCantidadHoras(cantidadHoras.toString())

        if(!empleadoValidation.isValid || !fechaValidation.isValid || !cantidadHorasValidation.isValid){
            _state.update {
                it.copy(
                    empleadoError = empleadoValidation.error,
                    fechaError = fechaValidation.error,
                    cantidadHorasError = cantidadHorasValidation.error
                )
            }
            return
        }

        val tipoValidation = validateTipoHoraExtra(state.value.tipo, cantidadHoras.toString())

        if(!tipoValidation.isValid){
            _state.update {
                it.copy(
                    tipoError = tipoValidation.error
                )
            }
            return
        }

        val empleado = state.value.empleados.find { it.empleadosId ==  empleadoId.toInt()}

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            if(empleado != null) {
                val esPuestoDireccion = (observeOcupacionUseCase().first().find { it.OcupacionId == empleado.ocupacionId }?.esPuestoDireccion ?: false)
                val recargo = calcularMontoHoraExtra(
                    sueldo = empleado.sueldo,
                    frecuenciaPago = empleado.frecuenciaPago,
                    tipoHoraExtra = tipo,
                    cantidadHoras = cantidadHoras.toInt(),
                    esPuestoDireccion = esPuestoDireccion
                )


                val horaExtra = HoraExtra(
                    horaExtraId = state.value.horaExtraId ?: 0,
                    empleadoId = empleadoId.toInt(),
                    fecha = fecha,
                    cantidadHoras = cantidadHoras.toInt(),
                    tipoHoraExtra = tipo,
                    recargo = recargo
                )


                val result = upsertHoraExtraUseCase(horaExtra)

                result.onSuccess { newId ->
                    _state.update {
                        it.copy(
                            isSaving = false,
                            saved = true,
                            horaExtraId = newId,
                            isNew = false
                        )
                    }
                }.onFailure { _state.update { it.copy(isSaving = false) } }
            }
        }
    }

    private fun onDelete(){
        val id = state.value.horaExtraId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteHoraExtraUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}