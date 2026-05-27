package com.android.registroempleados.data.mappers

import com.android.registroempleados.data.local.EmpleadosEntity
import com.android.registroempleados.data.local.HorasExtrasEntity
import com.android.registroempleados.domain.model.Empleados
import com.android.registroocupaciones.domain.horaextra.model.FrecuenciaPago


fun EmpleadosEntity.toDomain() = Empleados(
    empleadosId = empleadosId,
    ocupacionId = ocupacionId,
    nombres = nombres,
    sexo = sexo,
    fechaIngreso = fechaIngreso,
    sueldo = sueldo,
    frecuenciaPago = FrecuenciaPago.Semanal,
)

fun Empleados.toEntity() = EmpleadosEntity(
    empleadosId = empleadosId,
    ocupacionId = ocupacionId,
    nombres = nombres,
    sexo = sexo,
    fechaIngreso = fechaIngreso,
    sueldo = sueldo,
    frecuenciaPago = FrecuenciaPago.Semanal,
)