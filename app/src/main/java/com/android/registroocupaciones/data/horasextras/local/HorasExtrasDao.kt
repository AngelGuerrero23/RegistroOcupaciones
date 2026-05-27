package com.android.registroempleados.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
@Dao
interface HorasExtrasDao {
    @Upsert
    suspend fun upsert(entity: HorasExtrasEntity)

    @Query ("Select * from HoraExtra ORDER BY horaExtraId")
    fun observeAll(): Flow<List<HorasExtrasEntity>>

    @Query("Select * from HoraExtra WHERE horaExtraId=:id")
    suspend fun getById(id: Int): HorasExtrasEntity

    @Query("Delete from HoraExtra WHERE horaExtraId=:id")
    suspend fun deleteById(id: Int)

    @Query("Select exists(Select 1 from HoraExtra WHERE horaExtraId=:id)")
    suspend fun exists(id: Int): Boolean
}