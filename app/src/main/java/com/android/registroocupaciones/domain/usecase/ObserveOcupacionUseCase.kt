package com.android.registroocupaciones.domain.usecase

import com.android.registroocupaciones.domain.model.Ocupacion
import com.android.registroocupaciones.domain.repository.OcupacionRepository
import kotlinx.coroutines.flow.Flow

class ObserveOcupacionUseCase(
    private val repository: OcupacionRepository
){
    operator fun invoke(): Flow<List<Ocupacion>> = repository.observeOcupacion()
}