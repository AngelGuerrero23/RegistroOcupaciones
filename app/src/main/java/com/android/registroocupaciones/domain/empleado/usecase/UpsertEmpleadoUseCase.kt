package com.android.registroempleados.domain.usecase

import com.android.registroempleados.domain.model.Empleados
import com.android.registroempleados.domain.repository.EmpleadosRepository
import kotlinx.coroutines.flow.first

class UpsertEmpleadoUseCase(
    private val repository: EmpleadosRepository
) {
    suspend operator fun invoke(empleados: Empleados): Result<Int>
    {
        if (empleados.nombres.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre no puede estar vacío"))
        }
        if (empleados.nombres.trim().length < 3) {
            return Result.failure(IllegalArgumentException("El nombre debe tener al menos 3 caracteres"))
        }

        val regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$".toRegex()
        if (!empleados.nombres.matches(regex)) {
            return Result.failure(IllegalArgumentException("El nombre contiene caracteres inválidos"))
        }

        val sueldoResult = validateSueldo(empleados.sueldo.toString())
        if(!sueldoResult.isValid)
        {
            return Result.failure(IllegalArgumentException(sueldoResult.error))
        }
        return runCatching { repository.upsert(empleados) }
    }
}