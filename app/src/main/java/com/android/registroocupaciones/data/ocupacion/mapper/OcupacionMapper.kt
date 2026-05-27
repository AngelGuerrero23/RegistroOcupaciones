package com.android.registroocupaciones.data.ocupacion.mapper

import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import com.android.registroocupaciones.data.ocupacion.local.OcupacionEntity

fun OcupacionEntity.toDomain()= Ocupacion(
    OcupacionId = ocupacionId,
    Descripcion = descripcion,
    esPuestoDireccion = esPuestoDireccion
)

fun Ocupacion.toEntity() = OcupacionEntity(
    ocupacionId = OcupacionId,
    descripcion = Descripcion,
    esPuestoDireccion = esPuestoDireccion
)
