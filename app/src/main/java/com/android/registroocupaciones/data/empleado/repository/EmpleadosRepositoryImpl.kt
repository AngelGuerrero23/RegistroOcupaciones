package com.android.registroempleados.data.repository

import com.android.registroempleados.data.local.EmpleadosDao
import com.android.registroempleados.data.mappers.toDomain
import com.android.registroempleados.data.mappers.toEntity
import com.android.registroempleados.domain.model.Empleados
import com.android.registroocupaciones.domain.empleado.repository.EmpleadosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class EmpleadosRepositoryImpl @Inject constructor(
    private val dao: EmpleadosDao
): EmpleadosRepository{

    override fun observeEmpleados(): Flow<List<Empleados>> {
        return dao.observeAll().map {entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getEmpleados(id:Int): Empleados? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun upsert(empleados: Empleados): Int{
        dao.upsert(empleados.toEntity())
            return empleados.empleadosId?:0
    }

    override suspend fun delete(id: Int){
        dao.deleteById(id)
    }

    override suspend fun exists(id: Int): Boolean{
        return dao.exists(id)
    }
}
