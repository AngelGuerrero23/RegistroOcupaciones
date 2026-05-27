package com.android.registroempleados.domain.usecase

import com.android.registroocupaciones.domain.empleado.repository.EmpleadosRepository
import javax.inject.Inject

class DeleteEmpleadoUseCase @Inject constructor(
    private val repository : EmpleadosRepository
){
    suspend operator fun invoke(id: Int)= repository.delete(id)
}