package com.android.registroocupaciones.di

import android.content.Context
import androidx.room.Room
import com.android.registroempleados.data.local.EmpleadosDao
import com.android.registroempleados.data.repository.EmpleadosRepositoryImpl
import com.android.registroempleados.domain.model.Empleados
import com.android.registroempleados.domain.repository.EmpleadosRepository
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
    fun provideEmpleadoDao(db: RegistroDb): EmpleadosDao {
        return db.empleadosDao()
    }

    @Provides
    @Singleton
    fun provideEmpleadoRepository(dao: EmpleadosDao): EmpleadosRepository {
        return EmpleadosRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideGetEmpleadoUseCase(repository: EmpleadosRepository): GetEmpleadoUseCase {
        return GetEmpleadoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpsertEmpleadoUseCase(repository: EmpleadosRepository): UpsertEmpleadoUseCase {
        return UpsertEmpleadoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteEmpleadoUseCase(repository: EmpleadosRepository): DeleteEmpleadoUseCase {
        return DeleteEmpleadoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideObserveEmpleadoUseCase(repository: EmpleadosRepository): ObserveEmpleadoUseCase {
        return ObserveEmpleadoUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideOcupacionDao(db: RegistroDb): OcupacionDao {
        return db.ocupacionDao()
    }

    @Provides
    @Singleton
    fun provideOcupacionRepository(dao: OcupacionDao): OcupacionRepository {
        return OcupacionRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideGetOcupacionUseCase(repository: OcupacionRepository): GetOcupacionUseCase {
        return GetOcupacionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpsertOcupacionUseCase(repository: OcupacionRepository): UpsertOcupacionUseCase {
        return UpsertOcupacionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteOcupacionUseCase(repository: OcupacionRepository): DeleteOcupacionUseCase {
        return DeleteOcupacionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideObserveOcupacionUseCase(repository: OcupacionRepository): ObserveOcupacionUseCase {
        return ObserveOcupacionUseCase(repository)
    }
}
