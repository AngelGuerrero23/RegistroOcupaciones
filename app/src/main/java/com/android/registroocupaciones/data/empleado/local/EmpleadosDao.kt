package com.android.registroempleados.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
@Dao
interface EmpleadosDao {
    @Upsert
    suspend fun upsert(entity: EmpleadosEntity) : Long

    @Query ("Select * from Empleados ORDER BY EmpleadosId")
    fun observeAll(): Flow<List<EmpleadosEntity>>

    @Query("Select * from Empleados WHERE EmpleadosId=:id")
    suspend fun getById(id: Int): EmpleadosEntity

    @Query("Delete from Empleados WHERE EmpleadosId=:id")
    suspend fun deleteById(id: Int): Int

    @Query("Select exists(Select 1 from Empleados WHERE EmpleadosId=:id)")
    suspend fun exists(id: Int): Boolean
}