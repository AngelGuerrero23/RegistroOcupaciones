package com.android.registroocupaciones.domain.empleado.repository
import com.android.registroempleados.domain.model.Empleados
import kotlinx.coroutines.flow.Flow
interface   EmpleadosRepository {
    fun observeEmpleados(): Flow<List<Empleados>>
    suspend fun getEmpleados(id: Int) : Empleados?
    suspend fun upsert(empleados: Empleados) : Int
    suspend fun delete(id: Int)
    suspend fun exists(id: Int): Boolean
}