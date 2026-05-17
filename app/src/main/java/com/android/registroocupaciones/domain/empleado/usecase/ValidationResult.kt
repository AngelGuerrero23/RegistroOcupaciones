package com.android.registroempleados.domain.usecase

import com.android.registroempleados.domain.model.Empleados

data class ValidationResult(
    val isValid: Boolean,
    val error: String?= null
)

fun validateNombres(nombres: String, empleadosExistentes: List<String>) : ValidationResult {
    return when {
        nombres.isBlank() -> ValidationResult(false, "El nombre no puede estar vacío")
        nombres.length < 2 -> ValidationResult(
            false,
            "El nombre debe contener al menos 3 caracteres"
        )

        !nombres.all { it.isLetter() || it.isWhitespace() } -> (
                ValidationResult(
                    false, "El nombre no puede" +
                            " contener números ni caracteres especiales"
                ))

        nombres.contains("  ") -> ValidationResult(
            false,
            "El nombre no puede tener mas de dos espacios"
        )
        nombres.length >16 -> {
            ValidationResult(false,"El nombre no puede " +
                    " contener mas de 16 carácteres")
        }
        else -> ValidationResult(true)
    }
}


fun validateSexo(sexoSeleccionado: String): ValidationResult
{
    val sexo = sexoSeleccionado.trim()
    val opcionesValidas = listOf("Masculino", "Femenino", "Otros")
    return when{
        sexoSeleccionado.isBlank() ->ValidationResult(false,"Debe seleccionar una opción")
        opcionesValidas.none(){it.equals(sexo, ignoreCase = true)}->
            ValidationResult(false, "El sexo seleccionado no es válido")
        else-> ValidationResult(true)
    }
}

fun validateFecha(fecha: java.time.LocalDate): ValidationResult{
    val fechaActual = java.time.LocalDate.now()
    return when{
        fecha.isAfter(fechaActual) -> ValidationResult(false,"Le fecha de ingreso" +
        " no puede ser una fecha futura")
        fecha.isBefore(java.time.LocalDate.of(2000,1,1)) ->ValidationResult(
            false, "La fecha ingreso no puede ser anterior al año 2000")
        else -> ValidationResult(true)
    }
}

fun validateSueldo(sueldo: String): ValidationResult{
    return  when{
        sueldo.isBlank() -> ValidationResult(false,
            "El sueldo no puede estar vacio tu no cobra es")
        sueldo.toDoubleOrNull() == null -> ValidationResult(false,
            "Ingrese un sueldo valido")
        sueldo.toDouble() <= 0.0 -> ValidationResult(false,
            "El sueldo tiene que ser mayor que cero")
        else -> ValidationResult(true)
    }
}
