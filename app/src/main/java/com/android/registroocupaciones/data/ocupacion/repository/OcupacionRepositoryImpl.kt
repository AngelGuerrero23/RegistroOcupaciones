package com.android.registroocupaciones.data.ocupacion.repository

import com.android.registroocupaciones.data.ocupacion.local.OcupacionDao
import com.android.registroocupaciones.data.ocupacion.mapper.toDomain
import com.android.registroocupaciones.data.ocupacion.mapper.toEntity
import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import com.android.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OcupacionRepositoryImpl @Inject constructor(
    private val dao: OcupacionDao
): OcupacionRepository {
    override fun observeOcupacion(): Flow<List<Ocupacion>> {
        return dao.observeAll().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getOcupacion(id: Int): Ocupacion? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun upsert(ocupacion: Ocupacion): Int {
        dao.upsert(ocupacion.toEntity())
        return ocupacion.OcupacionId?: 0
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun exists(id: Int): Boolean{
        return dao.exists(id)
    }
}
