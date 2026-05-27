package com.android.registroocupaciones.domain.horaextra.repository

import com.android.registroocupaciones.domain.horaextra.model.HoraExtra
import kotlinx.coroutines.flow.Flow

interface HoraExtraRepository {
    fun observeHoraExtra(): Flow<List<HoraExtra>>
    suspend fun getHoraExtra(id: Int) : HoraExtra?
    suspend fun upsert(horaExtra: HoraExtra) : Int
    suspend fun delete(id: Int)
    suspend fun exist(id: Int): Boolean
}