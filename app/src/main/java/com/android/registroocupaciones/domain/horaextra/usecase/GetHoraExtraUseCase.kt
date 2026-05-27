package com.android.registroocupaciones.domain.horaextra.usecase

import com.android.registroocupaciones.domain.horaextra.repository.HoraExtraRepository

class GetHoraExtraUseCase (
    private val repository: HoraExtraRepository
) {
    suspend operator fun invoke(id: Int) = repository.getHoraExtra(id)
}