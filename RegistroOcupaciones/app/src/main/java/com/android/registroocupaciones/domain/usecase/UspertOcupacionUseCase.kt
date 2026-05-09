package com.android.registroocupaciones.domain.usecase

import com.android.registroocupaciones.domain.model.Ocupacion
import com.android.registroocupaciones.domain.repository.OcupacionRepository

class UspertOcupacionUseCase(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(ocupacion: Ocupacion): Result<Int>
    {
        val descriptionResult = validateDescripcion(ocupacion.Descripcion)
        if(!descriptionResult.isValid){
            return Result.failure(IllegalArgumentException(descriptionResult.error))
        }
    }
}