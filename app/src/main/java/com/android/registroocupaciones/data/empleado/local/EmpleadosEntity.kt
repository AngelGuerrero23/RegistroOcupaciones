package com.android.registroempleados.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.registroocupaciones.domain.horaextra.model.FrecuenciaPago
import java.time.LocalDate

@Entity(tableName = "Empleados")
data class EmpleadosEntity(
    @PrimaryKey(autoGenerate = true)
    val empleadosId: Int = 0,
    val ocupacionId: Int=0,
    val nombres: String = "",
    val sexo: String ="",
    val fechaIngreso: LocalDate = LocalDate.now(),
    val sueldo: Double = 0.0,
    val frecuenciaPago: FrecuenciaPago = FrecuenciaPago.Semanal,
)
