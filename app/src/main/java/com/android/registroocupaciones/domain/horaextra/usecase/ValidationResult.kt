package com.android.registroocupaciones.domain.horaextra.usecase

import com.android.registroempleados.domain.usecase.ValidationResult
import com.android.registroocupaciones.domain.horaextra.model.TipoHoraExtra
import java.time.LocalDate

fun validateEmpleadoId(empleadoId: Int): ValidationResult {
    return when {
        empleadoId <= 0 -> ValidationResult(false, "Debe seleccionar un empleadoId")
        else -> ValidationResult(true)
    }
}

fun validateCantidadHoras(cantidad: String): ValidationResult {
    return when {
        cantidad.isBlank() -> ValidationResult(false, "La cantidad de horas no puede estar vacía")
        cantidad.toIntOrNull() == null -> ValidationResult(false, "Debe ingresar una cantidad válida")
        cantidad.toInt() <= 0 -> ValidationResult(false, "La cantidad de horas debe ser mayor que 0")
        cantidad.toInt() > 80 -> ValidationResult(false, "No puedes registrar mas de 80 horas en una semana")
        else -> ValidationResult(true)
    }
}

fun validateTipoHoraExtra(
    horaExtraSeleccionada: TipoHoraExtra?,
    horasInput : String,
): ValidationResult{
    val horasNuevas = horasInput.toIntOrNull()?: 0
    val totalHoras = horasNuevas
    val limiteHorasNormales = 24

    return when{
        horaExtraSeleccionada == null -> ValidationResult(
            false,
            "Debe seleccionar un tipo de hora extra"
        )
        totalHoras > limiteHorasNormales && horaExtraSeleccionada != TipoHoraExtra.AltoVolumen ->{
            ValidationResult(false, "Al pasar de 24 hrs extras (68h totales), la ley exige usar 'Alto Volumen'."
            )
        }
        else -> ValidationResult(true)
    }
}


fun validateFechaHoraExtra(fecha: LocalDate): ValidationResult {
    val hoy = LocalDate.now()
    return when {
        fecha.isAfter(hoy) -> ValidationResult(false, "La fecha no puede ser futura")
        fecha.isBefore(java.time.LocalDate.of(2000,1,1)) ->ValidationResult(
            false, "La fecha ingreso no puede ser anterior al año 2000")
        else -> ValidationResult(true)
    }
}