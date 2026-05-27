package com.android.registroempleados.domain.model

import com.android.registroocupaciones.domain.horaextra.model.FrecuenciaPago
import java.time.LocalDate

data class Empleados(
    val empleadosId: Int = 0,
    val ocupacionId: Int=0,
    val nombres: String = "",
    val sexo: String ="",
    val fechaIngreso: LocalDate = LocalDate.now(),
    val sueldo: Double = 0.0,
    val frecuenciaPago: FrecuenciaPago = FrecuenciaPago.Semanal,
)


