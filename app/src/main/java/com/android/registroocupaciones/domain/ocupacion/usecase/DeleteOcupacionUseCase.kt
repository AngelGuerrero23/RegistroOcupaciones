package com.android.registroocupaciones.domain.ocupacion.usecase

import com.android.registroocupaciones.domain.ocupacion.repository.OcupacionRepository

class DeleteOcupacionUseCase(
    private val repository: OcupacionRepository
)
{
    suspend operator fun invoke(id: Int)= repository.delete(id)
}