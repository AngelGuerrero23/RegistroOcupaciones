package com.android.registroocupaciones.data.ocupacion.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ocupaciones")
data class OcupacionEntity(
    @PrimaryKey(autoGenerate = true)
    val ocupacionId: Int,
    val descripcion: String,
    val esPuestoDireccion: Boolean = false
)
