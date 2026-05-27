package com.android.registroempleados.domain.usecase

import com.android.registroempleados.domain.model.Empleados
import com.android.registroocupaciones.domain.empleado.repository.EmpleadosRepository
import java.time.LocalDate
import javax.inject.Inject

class UpsertEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadosRepository
) {
    suspend operator fun invoke(empleados: Empleados): Result<Int>
    {
        val nombresResult = validateNombres(empleados.nombres)
        if (!nombresResult.isValid) {
            return Result.failure(IllegalArgumentException(nombresResult.error))
        }

        val fechaIngresoResult = validateFecha(empleados.fechaIngreso)
        if (!fechaIngresoResult.isValid) {
            return Result.failure(IllegalArgumentException(fechaIngresoResult.error))
        }

        val sueldoResult = validateSueldo(empleados.sueldo.toString())
        if(!sueldoResult.isValid)
        {
            return Result.failure(IllegalArgumentException(sueldoResult.error))
        }

        val sexoResult = validateSexo(empleados.sexo)
        if(!sexoResult.isValid){
            return Result.failure(IllegalArgumentException(sexoResult.error))
        }
        val ocupacionResult = validateOcupacionId(empleados.ocupacionId)
        if(!ocupacionResult.isValid){
            return Result.failure(IllegalArgumentException(ocupacionResult.error))
        }

        val frecuenciaPagoResult = validateFrecuenciaPago(empleados.frecuenciaPago.toString())
        if (!frecuenciaPagoResult.isValid){
            return Result.failure(IllegalArgumentException(frecuenciaPagoResult.error))
        }

        return runCatching { repository.upsert(empleados) }
    }
}