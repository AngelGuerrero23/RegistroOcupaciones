package com.android.registroocupaciones.data.local

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface OcupacionDao {
    @Upsert
    suspend fun upsert(entity: OcupacionEntity)
    @Delete
    suspend fun delete(entity: Int)
    @Query("Select * from Ocupaciones ORDER BY OcupacionId DESC")
    fun observeAll(): Flow<List<OcupacionEntity>>

    @Query("Select * from Ocupaciones WHERE OcupacionId=:id")
    suspend fun getById(id: Int): OcupacionEntity?

    @Query("Delete from Ocupaciones WHERE OcupacionId=:id")
    suspend fun deleteById(id: Int)

    @Query("Select exists (Select 1 from Ocupaciones WHERE OcupacionId=:id)")
    suspend fun exists(id: Int): Boolean

}