package com.android.registroocupaciones.data.ocupacion.mapper

import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import com.android.registroocupaciones.data.ocupacion.local.OcupacionEntity

fun OcupacionEntity.toDomain()= Ocupacion(
    OcupacionId = OcupacionId,
    Descripcion = Descripcion,
    Sueldo = Sueldo
)

fun Ocupacion.toEntity() = OcupacionEntity(
    OcupacionId = OcupacionId,
    Descripcion = Descripcion,
    Sueldo = Sueldo
)
