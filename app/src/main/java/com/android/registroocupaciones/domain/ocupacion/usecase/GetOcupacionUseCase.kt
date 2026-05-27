package com.android.registroocupaciones.domain.ocupacion.usecase

import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import com.android.registroocupaciones.domain.ocupacion.repository.OcupacionRepository

class GetOcupacionUseCase(
    private val repository: OcupacionRepository
)
{
    suspend operator fun invoke(id: Int) = repository.getOcupacion(id)
}