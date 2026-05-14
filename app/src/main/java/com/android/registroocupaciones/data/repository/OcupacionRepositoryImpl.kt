package com.android.registroocupaciones.data.repository

import com.android.registroocupaciones.data.local.OcupacionDao
import com.android.registroocupaciones.data.mapper.toDomain
import com.android.registroocupaciones.data.mapper.toEntity
import com.android.registroocupaciones.domain.model.Ocupacion
import com.android.registroocupaciones.domain.repository.OcupacionRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

class OcupacionRepositoryImpl(
    private val dao: OcupacionDao
): OcupacionRepository {
    override fun observeOcupacion(): Flow<List<Ocupacion>> = dao.observeAll().map {
        list-> list.map { it.toDomain() }
    }

    override suspend fun getOcupacion(id: Int): Ocupacion? = dao.getById(id)?.toDomain()

    override suspend fun upsert(ocupacion: Ocupacion): Int {
        return dao.upsert(ocupacion.toEntity()).toInt()
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}
