package com.android.registroocupaciones.data.empleado.local

import androidx.room.TypeConverter
import com.android.registroocupaciones.domain.horaextra.model.FrecuenciaPago
import com.android.registroocupaciones.domain.horaextra.model.TipoHoraExtra
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromString(value:String?):LocalDate?{
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun toString(date: LocalDate?): String?
    {
        return date?.toString()
    }

    @TypeConverter
    fun fromTipoHoraExtra(value: TipoHoraExtra?): String?
    {
        return value?.name
    }

    @TypeConverter
    fun toTipoHoraExtra(value: String?): TipoHoraExtra?{
        return value?.let { TipoHoraExtra.valueOf(it) }
    }

    @TypeConverter
    fun fromFrecuenciaPago(value: FrecuenciaPago?): String?{
        return value?.name
    }
    @TypeConverter
    fun toFrecuenciaPago(value: String): FrecuenciaPago {
        return FrecuenciaPago.valueOf(value)
    }
}