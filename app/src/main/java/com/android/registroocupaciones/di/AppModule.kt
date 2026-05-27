package com.android.registroocupaciones.di

import android.content.Context
import androidx.room.Room
import com.android.registroempleados.data.local.EmpleadosDao
import com.android.registroempleados.data.local.HorasExtrasDao
import com.android.registroempleados.data.repository.EmpleadosRepositoryImpl
import com.android.registroocupaciones.domain.empleado.repository.EmpleadosRepository
import com.android.registroempleados.domain.usecase.DeleteEmpleadoUseCase
import com.android.registroempleados.domain.usecase.GetEmpleadoUseCase
import com.android.registroempleados.domain.usecase.ObserveEmpleadoUseCase
import com.android.registroempleados.domain.usecase.UpsertEmpleadoUseCase
import com.android.registroocupaciones.data.database.RegistroDb
import com.android.registroocupaciones.data.ocupacion.local.OcupacionDao
import com.android.registroocupaciones.data.ocupacion.repository.OcupacionRepositoryImpl
import com.android.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import com.android.registroocupaciones.domain.ocupacion.usecase.DeleteOcupacionUseCase
import com.android.registroocupaciones.domain.ocupacion.usecase.GetOcupacionUseCase
import com.android.registroocupaciones.domain.ocupacion.usecase.ObserveOcupacionUseCase
import com.android.registroocupaciones.domain.ocupacion.usecase.UpsertOcupacionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOcupacionDb(@ApplicationContext context: Context): RegistroDb {
        return Room.databaseBuilder(
            context,
            RegistroDb::class.java,
            "ocupaciones_db",

        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideOcupacionDao(database: RegistroDb): OcupacionDao {
        return database.ocupacionDao()
    }

    @Provides
    @Singleton
    fun provideEmpleadoDao(database: RegistroDb): EmpleadosDao {
        return database.empleadosDao()
    }

    @Provides
    @Singleton
    fun provideHoraExtraDao(database: RegistroDb): HorasExtrasDao {
        return database.horasExtrasDao()
    }
}
