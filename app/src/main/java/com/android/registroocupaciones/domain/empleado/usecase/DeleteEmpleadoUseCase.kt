package com.android.registroempleados.domain.usecase

import com.android.registroempleados.domain.repository.EmpleadosRepository

class DeleteEmpleadoUseCase (
    private val repository : EmpleadosRepository
){
    suspend operator fun invoke(id: Int)= repository.delete(id)
}