package com.android.registroocupaciones.domain.ocupacion.usecase

import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import com.android.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import kotlinx.coroutines.flow.first

class UpsertOcupacionUseCase(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(ocupacion: Ocupacion): Result<Int>
    {
        val listaActual = repository.observeOcupacion().first().map { it.Descripcion }
        val descriptionResult = validateDescripcion(ocupacion.Descripcion, listaActual)
        if(!descriptionResult.isValid){
            return Result.failure(IllegalArgumentException(descriptionResult.error))
        }
        val sueldoResult = validateSueldo(ocupacion.Sueldo.toString())
        if(!sueldoResult.isValid)
        {
            return Result.failure(IllegalArgumentException(sueldoResult.error))
        }
        return runCatching{repository.upsert(ocupacion)}
    }

}

