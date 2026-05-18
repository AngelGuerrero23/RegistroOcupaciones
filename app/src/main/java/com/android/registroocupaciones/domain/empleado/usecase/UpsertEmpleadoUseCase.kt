package com.android.registroempleados.domain.usecase

import com.android.registroempleados.domain.model.Empleados
import com.android.registroempleados.domain.repository.EmpleadosRepository
import kotlinx.coroutines.flow.first

class UpsertEmpleadoUseCase(
    private val repository: EmpleadosRepository
) {
    suspend operator fun invoke(empleados: Empleados): Result<Int>
    {
        val listaActual = repository.observeEmpleados().first().map { it.nombres }
        val nombresResult = validateNombres(empleados.nombres, listaActual)
        if(!nombresResult.isValid){
            return Result.failure(IllegalArgumentException(nombresResult.error))
        }
        val sueldoResult = validateSueldo(empleados.sueldo.toString())
        if(!sueldoResult.isValid)
        {
            return Result.failure(IllegalArgumentException(sueldoResult.error))
        }
        return runCatching { repository.upsert(empleados) }
    }
}