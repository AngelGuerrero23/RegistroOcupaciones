package com.android.registroocupaciones.data.ocupacion.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ocupaciones")
data class OcupacionEntity(
    @PrimaryKey(autoGenerate = true)
    val OcupacionId: Int = 0,
    val Descripcion: String ="",
    val Sueldo: Double = 0.0,
)
