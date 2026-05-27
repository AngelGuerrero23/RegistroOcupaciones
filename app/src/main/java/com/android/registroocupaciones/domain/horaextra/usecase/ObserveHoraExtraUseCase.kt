package com.android.registroocupaciones.domain.horaextra.usecase

import com.android.registroocupaciones.domain.horaextra.model.HoraExtra
import com.android.registroocupaciones.domain.horaextra.repository.HoraExtraRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveHoraExtraUseCase @Inject constructor(
    private val repository: HoraExtraRepository
){
    operator fun invoke(): Flow<List<HoraExtra>> = repository.observeHoraExtra()
}