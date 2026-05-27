package com.android.registroempleados.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.android.registroocupaciones.domain.horaextra.model.TipoHoraExtra
import java.time.LocalDate

@Entity(tableName = "HoraExtra",
    foreignKeys = [
        ForeignKey(
            entity = EmpleadosEntity::class,
            parentColumns = ["empleadosId"],
            childColumns = ["empleadosId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("empleadosId")]
)
data class HorasExtrasEntity(
    @PrimaryKey(autoGenerate = true)
    var horaExtraId: Int = 0,
    var empleadosId: Int =0,
    var fecha: LocalDate = LocalDate.now(),
    var cantidadHoras: Int = 0,
    var tipohoraExtra: TipoHoraExtra = TipoHoraExtra.Diurna,
    var recargo: Double = 0.0,
    var esPuestoDireccion: Boolean = false
)
