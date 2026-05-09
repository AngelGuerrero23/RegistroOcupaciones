package com.android.registroocupaciones.domain.usecase

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
    return when{
        sueldo.isBlank()-> ValidationResult(false,"El sueldo no puede estár vacío")
        sueldo.toDouble() <=0 -> ValidationResult(false,"El sueldo debe ser mayor a 0")
        sueldo.toDoubleOrNull() == null -> ValidationResult(false,"El sueldo debe ser numero")
        else-> ValidationResult(true)
    }
}


