package com.android.registroocupaciones.domain.ocupacion.usecase

import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import com.android.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository
){
    operator fun invoke(): Flow<List<Ocupacion>> = repository.observeOcupacion()
}