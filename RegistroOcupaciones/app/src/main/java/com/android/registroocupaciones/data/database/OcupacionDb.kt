package com.android.registroocupaciones.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.registroocupaciones.data.local.OcupacionDao
import com.android.registroocupaciones.data.local.OcupacionEntity
import com.android.registroocupaciones.domain.model.Ocupacion

@Database(
    entities = [
        OcupacionEntity:: class,
    ],
    version = 1,
    exportSchema = false
)

abstract class OcupacionDb : RoomDatabase() {
    abstract fun OcupacionDb(): OcupacionDao
}