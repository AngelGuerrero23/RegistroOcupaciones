package com.android.registroocupaciones.data.ocupacion.repository

import com.android.registroocupaciones.data.ocupacion.local.OcupacionDao
import com.android.registroocupaciones.data.ocupacion.mapper.toDomain
import com.android.registroocupaciones.data.ocupacion.mapper.toEntity
import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import com.android.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
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
