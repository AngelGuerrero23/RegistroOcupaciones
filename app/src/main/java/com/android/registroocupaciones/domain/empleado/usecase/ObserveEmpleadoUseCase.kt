package com.android.registroempleados.domain.usecase

import com.android.registroempleados.domain.model.Empleados
import com.android.registroempleados.domain.repository.EmpleadosRepository
import kotlinx.coroutines.flow.Flow

class ObserveEmpleadoUseCase(
    private val repository: EmpleadosRepository
) {
    operator fun invoke(): Flow<List<Empleados>> = repository.observeEmpleados()
}