package com.android.registroempleados.domain.model

import java.time.LocalDate

data class Empleados(
    val empleadosId: Int = 0,
    val nombres: String = "",
    val fechaIngreso: LocalDate = LocalDate.now(),
    val sexo: String ="",
    val sueldo: Double = 0.0
)


