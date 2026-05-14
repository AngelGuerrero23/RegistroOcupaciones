package com.android.registroocupaciones.domain.usecase

import com.android.registroocupaciones.domain.repository.OcupacionRepository

class DeleteOcupacionUseCase(
    private val repository: OcupacionRepository
)
{
    suspend operator fun invoke(id: Int)= repository.delete(id)
}