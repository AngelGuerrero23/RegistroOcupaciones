package com.android.registroempleados.domain.usecase

import com.android.registroocupaciones.domain.empleado.repository.EmpleadosRepository

class DeleteEmpleadoUseCase (
    private val repository : EmpleadosRepository
){
    suspend operator fun invoke(id: Int)= repository.delete(id)
}