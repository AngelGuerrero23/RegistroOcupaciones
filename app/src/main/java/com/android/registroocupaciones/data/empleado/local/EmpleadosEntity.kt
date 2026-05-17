package com.android.registroempleados.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "Empleados")
data class EmpleadosEntity(
    @PrimaryKey(autoGenerate = true)
    val EmpleadosId: Int = 0,
    val Nombres: String = "",
    val FechaIngreso: LocalDate,
    val Sexo: String = "",
    val Sueldo: Double = 0.0
)
