package com.android.registroempleados.data.repository

import com.android.registroempleados.data.local.EmpleadosDao
import com.android.registroempleados.data.mappers.toDomain
import com.android.registroempleados.data.mappers.toEntity
import com.android.registroempleados.domain.model.Empleados
import com.android.registroempleados.domain.repository.EmpleadosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class EmpleadosRepositoryImpl(
    private val dao: EmpleadosDao
): EmpleadosRepository{
    override fun observeEmpleados(): Flow<List<Empleados>> = dao.observeAll().map {
        list-> list.map { it.toDomain() }
    }

    override suspend fun getEmpleados(id:Int): Empleados? = dao.getById(id)?.toDomain()

    override suspend fun upsert(empleados: Empleados): Int{
        dao.upsert(empleados.toEntity())
            return empleados.empleadosId?:0
    }

    override suspend fun delete(id: Int){
        dao.deleteById(id)
    }
}
