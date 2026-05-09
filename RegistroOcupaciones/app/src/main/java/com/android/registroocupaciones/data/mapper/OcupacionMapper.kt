package com.android.registroocupaciones.data.mapper

import com.android.registroocupaciones.domain.model.Ocupacion
import com.android.registroocupaciones.data.local.OcupacionEntity

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
