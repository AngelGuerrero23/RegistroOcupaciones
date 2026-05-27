package com.android.registroocupaciones.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.registroempleados.data.local.EmpleadosDao
import com.android.registroempleados.data.local.EmpleadosEntity
import com.android.registroempleados.data.local.HorasExtrasDao
import com.android.registroempleados.data.local.HorasExtrasEntity
import com.android.registroocupaciones.data.empleado.local.Converters
import com.android.registroocupaciones.data.ocupacion.local.OcupacionDao
import com.android.registroocupaciones.data.ocupacion.local.OcupacionEntity

@Database(
    entities = [
        OcupacionEntity::class,
        EmpleadosEntity::class,
        HorasExtrasEntity::class,
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RegistroDb : RoomDatabase() {
    abstract fun ocupacionDao(): OcupacionDao
    abstract fun empleadosDao(): EmpleadosDao
    abstract fun horasExtrasDao(): HorasExtrasDao

}