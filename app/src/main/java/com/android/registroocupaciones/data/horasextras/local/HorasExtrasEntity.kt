package com.android.registroempleados.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.registroocupaciones.domain.horaextra.model.TipoHoraExtra
import java.time.LocalDate

@Entity(tableName = "HoraExtra")
data class HorasExtrasEntity(
    @PrimaryKey(autoGenerate = true)
    var horaExtraId: Int = 0,
    var empleadoId: Int =0,
    var fecha: LocalDate = LocalDate.now(),
    var cantidadHoras: Int = 0,
    var tipo: TipoHoraExtra = TipoHoraExtra.Diurna,
    var recargo: Double = 0.0,
    var esPuestoDireccion: Boolean = false
)
