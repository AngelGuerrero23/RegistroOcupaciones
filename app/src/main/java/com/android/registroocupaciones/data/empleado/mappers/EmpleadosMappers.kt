package com.android.registroempleados.data.mappers

import com.android.registroempleados.data.local.EmpleadosEntity
import com.android.registroempleados.domain.model.Empleados

fun EmpleadosEntity.toDomain() = Empleados(
    empleadosId = EmpleadosId,
    nombres = Nombres,
    fechaIngreso = FechaIngreso,
    sexo = Sexo,
    sueldo = Sueldo
)

fun Empleados.toEntity() = EmpleadosEntity(
    EmpleadosId = empleadosId,
    Nombres = nombres,
    FechaIngreso = fechaIngreso,
    Sexo = sexo,
    Sueldo = sueldo
)