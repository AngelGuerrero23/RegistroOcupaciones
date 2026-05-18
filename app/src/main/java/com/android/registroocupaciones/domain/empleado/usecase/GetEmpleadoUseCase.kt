package com.android.registroempleados.domain.usecase

import com.android.registroempleados.domain.model.Empleados
import com.android.registroempleados.domain.repository.EmpleadosRepository

class GetEmpleadoUseCase(
    private val repository: EmpleadosRepository)

{
    suspend operator fun invoke(id: Int): Empleados?= repository.getEmpleados(id)
}