package com.android.registroempleados.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.android.registroocupaciones.data.ocupacion.local.OcupacionEntity
import com.android.registroocupaciones.domain.horaextra.model.FrecuenciaPago
import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import java.time.LocalDate

@Entity(tableName = "empleados",
    foreignKeys = [
        ForeignKey(
        entity = OcupacionEntity::class,
    parentColumns = ["ocupacionId"],
    childColumns = ["ocupacionId"],
    onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("ocupacionId")]
)

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
