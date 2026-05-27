package com.android.registroocupaciones.domain.ocupacion.usecase

import com.android.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import javax.inject.Inject

class DeleteOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository
)
{
    suspend operator fun invoke(id: Int)= repository.delete(id)
}