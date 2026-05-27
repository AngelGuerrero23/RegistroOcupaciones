package com.android.registroocupaciones.data.horasextras.repository

import com.android.registroempleados.data.local.HorasExtrasDao
import com.android.registroocupaciones.data.horasextras.mappers.toDomain
import com.android.registroocupaciones.data.horasextras.mappers.toEntity
import com.android.registroocupaciones.domain.horaextra.model.HoraExtra
import com.android.registroocupaciones.domain.horaextra.repository.HoraExtraRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class HoraExtraRepositoryImpl @Inject constructor(
    private val dao: HorasExtrasDao
) : HoraExtraRepository{

    override fun observeHoraExtra(): Flow<List<HoraExtra>>{
        return dao.observeAll().map { entities -> entities.map { it.toDomain()}}
    }

    override suspend fun getHoraExtra(id: Int): HoraExtra?{
        return dao.getById(id)?.toDomain()
    }

    override suspend fun upsert(horaExtra: HoraExtra): Int{
        dao.upsert(horaExtra.toEntity())
        return horaExtra.horaExtraId?:0
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun exist(id: Int): Boolean {
        return dao.exists(id)
    }

}