package com.android.registroocupaciones.data.horasextras.mappers

import com.android.registroempleados.data.local.HorasExtrasEntity
import com.android.registroocupaciones.domain.horaextra.model.HoraExtra


fun HorasExtrasEntity.toDomain() = HoraExtra(
    horaExtraId = horaExtraId,
    empleadoId = empleadoId,
    fecha = fecha,
    cantidadHoras = cantidadHoras,
    tipoHoraExtra = tipohoraExtra,
    recargo = recargo,
    esPuestoDireccion = esPuestoDireccion

)

fun HoraExtra.toEntity() = HorasExtrasEntity(
    horaExtraId = horaExtraId,
    empleadoId = empleadoId,
    fecha = fecha,
    cantidadHoras = cantidadHoras,
    tipohoraExtra = tipoHoraExtra,
    recargo = recargo,
    esPuestoDireccion = esPuestoDireccion
)