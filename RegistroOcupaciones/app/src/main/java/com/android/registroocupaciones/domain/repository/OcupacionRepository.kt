package com.android.registroocupaciones.domain.repository


import com.android.registroocupaciones.domain.model.Ocupacion
import kotlinx.coroutines.flow.Flow

interface OcupacionRepository {
    fun observeOcupacion(): Flow<List<Ocupacion>>
    suspend fun  getOcupacion(id: Int): Ocupacion?
    suspend fun upsert(ocupacion: Ocupacion): Int
    suspend fun delete(id:Int)
}