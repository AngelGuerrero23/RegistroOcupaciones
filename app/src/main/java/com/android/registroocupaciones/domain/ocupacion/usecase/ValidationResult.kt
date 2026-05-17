package com.android.registroocupaciones.domain.ocupacion.usecase

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)

fun validateDescripcion(descripcion: String, ocupacionesExistentes: List<String>): ValidationResult
{
    return when{
        descripcion.isBlank() -> ValidationResult(false,"La descripción no puede estar vacía")
        descripcion.length < 3 -> ValidationResult(false, "La descripción debe contener almenos 3 carácter")

        ocupacionesExistentes.any{it.equals(descripcion.trim(), ignoreCase = true)}->
            ValidationResult(false,"Esta ocupación ya está registrada")
        else -> ValidationResult(true)
    }
}
fun validateSueldo(sueldo: String): ValidationResult
{
    val sueldoDouble = sueldo.toDoubleOrNull()
    return when{
        sueldo.isBlank()-> ValidationResult(false,"El sueldo no puede estár vacío")
        sueldoDouble == null -> ValidationResult(false,"El sueldo debe ser numero")
        sueldoDouble <= 0 -> ValidationResult(false,"El sueldo debe ser mayor a 0")
        else-> ValidationResult(true)
    }
}


