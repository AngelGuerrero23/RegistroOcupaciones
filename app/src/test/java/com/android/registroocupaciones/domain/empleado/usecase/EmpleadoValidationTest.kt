package com.android.registroocupaciones.domain.empleado.usecase

import androidx.test.services.events.run.TestRunEvent
import com.android.registroempleados.domain.usecase.ValidationResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.rules.TestRule
import java.time.LocalDate

@ExperimentalCoroutinesApi
data class EmpleadoValidationTest(
    val isValid: Boolean,
    val error: String?=null
)

fun validateNombres(nombres: String): ValidationResult{
    return when{
        nombres.isBlank()-> ValidationResult(false, "El nombre no puede estar vacio")
        nombres.trim().length <3 -> ValidationResult(false,"El nombre debe tener al menos 3 letras")
        !nombres.all { it.isLetter() || it.isWhitespace() }-> ValidationResult(false,
            "El nombre solo debe contener letras")
        else -> ValidationResult(true)
    }

}

fun validateSueldo(sueldo: String): ValidationResult{
    return when {
        sueldo.isBlank()-> ValidationResult(false, "El sueldo no puede estar vacio")
        sueldo.toDoubleOrNull() == null -> ValidationResult(false, "Debe ingresar un monto valido")
        sueldo.toDouble() <=0.0 -> ValidationResult(false, "El monto debe ser mayor que 0")
        else -> ValidationResult(true)
    }
}

fun validateFecha(fecha: LocalDate): ValidationResult{
    val hoy = LocalDate.now()
    return when{
        fecha.isAfter(hoy) -> ValidationResult(false, "La fecha de ingreso no puede ser futura")
        else-> ValidationResult(true)
    }
}

fun validateSexo(sexo: String): ValidationResult {
    return if (sexo.isBlank()) {
        ValidationResult(false, "Debe seleccionar el genero del empleado")
    } else {
        ValidationResult(true)
    }
}
