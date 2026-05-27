package com.android.registroempleados.data.mappers

import com.android.registroempleados.data.local.EmpleadosEntity
import com.android.registroempleados.domain.model.Empleados


fun EmpleadosEntity.toDomain() = Empleados(
    empleadosId = empleadosId,
    ocupacionId = ocupacionId,
    nombres = nombres,
    sexo = sexo,
    fechaIngreso = fechaIngreso,
    sueldo = sueldo,
    frecuenciaPago = frecuenciaPago
)

fun Empleados.toEntity() = EmpleadosEntity(
    empleadosId = empleadosId,
    ocupacionId = ocupacionId,
    nombres = nombres,
    sexo = sexo,
    fechaIngreso = fechaIngreso,
    sueldo = sueldo,
    frecuenciaPago = frecuenciaPago
)