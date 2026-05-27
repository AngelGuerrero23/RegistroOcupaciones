package com.android.registroempleados.domain.usecase

import com.android.registroempleados.domain.model.Empleados
import com.android.registroocupaciones.domain.empleado.repository.EmpleadosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadosRepository
) {
    operator fun invoke(): Flow<List<Empleados>> = repository.observeEmpleados()
}