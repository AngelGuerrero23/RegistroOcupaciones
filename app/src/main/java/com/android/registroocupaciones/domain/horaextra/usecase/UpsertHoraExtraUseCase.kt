package com.android.registroocupaciones.domain.horaextra.usecase

import com.android.registroocupaciones.domain.horaextra.model.HoraExtra
import com.android.registroocupaciones.domain.horaextra.repository.HoraExtraRepository
import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import com.android.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import javax.inject.Inject

class UpsertHoraExtraUseCase @Inject constructor(
    private val repository: HoraExtraRepository
) {
    suspend operator fun invoke(horaExtra: HoraExtra): Result<Int> {
        val empleadoResult = validateEmpleadoId(horaExtra.empleadoId)
        if (!empleadoResult.isValid) {
            return Result.failure(IllegalArgumentException(empleadoResult.error))
        }

        val fechaResult = validateFechaHoraExtra(horaExtra.fecha)
        if (!fechaResult.isValid) {
            return Result.failure(IllegalArgumentException(fechaResult.error))
        }

        val horasResult = validateCantidadHoras(horaExtra.cantidadHoras.toString())
        if (!horasResult.isValid) {
            return Result.failure(IllegalArgumentException(horasResult.error))
        }

        val tipoResult = validateTipoHoraExtra(horaExtra.tipoHoraExtra, horaExtra.cantidadHoras.toString())
        if (!tipoResult.isValid) {
            return Result.failure(IllegalArgumentException(tipoResult.error))
        }

        return runCatching { repository.upsert(horaExtra) }
    }
}