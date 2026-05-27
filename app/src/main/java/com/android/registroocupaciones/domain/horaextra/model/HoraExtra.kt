package com.android.registroocupaciones.domain.horaextra.model

import java.time.LocalDate

data class HoraExtra (
    var horaExtraId : Int = 0,
    var empleadoId: Int = 0,
    var fecha: LocalDate = LocalDate.now(),
    var cantidadHoras : Int = 0,
    var tipoHoraExtra : TipoHoraExtra = TipoHoraExtra.Diurna,
    var recargo : Double = 0.0,
    var esPuestoDireccion : Boolean = false
)
enum class TipoHoraExtra(val descripcion: String, val factor: Double){
    Diurna("Diurna", 1.35),
    Nocturna("Nocturna", 1.5),
    DiaLibre("Dia Libre", 2.0),
    DiaFeriado("Dia Feriado", 2.0),
    AltoVolumen("Alto Volumen", 2.0)
}

enum class FrecuenciaPago(val descripcion: String, val divisor: Double){
    Semanal("Semanal", 5.5),
    Quincenal("Quincenal", 11.91),
    Mensual("Mensual", 23.83)
}