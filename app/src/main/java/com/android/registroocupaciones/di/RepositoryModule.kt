package com.android.registroocupaciones.di

import com.android.registroempleados.data.repository.EmpleadosRepositoryImpl
import com.android.registroocupaciones.data.horasextras.repository.HoraExtraRepositoryImpl
import com.android.registroocupaciones.data.ocupacion.repository.OcupacionRepositoryImpl
import com.android.registroocupaciones.domain.empleado.repository.EmpleadosRepository
import com.android.registroocupaciones.domain.horaextra.repository.HoraExtraRepository
import com.android.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
abstract class RepositoryModule{
    @Binds
    @Singleton
    abstract fun bindOcupacionRepository(
        impl: OcupacionRepositoryImpl
    ): OcupacionRepository

    @Binds
    @Singleton
    abstract fun bindEmpleadoRepository(
        impl: EmpleadosRepositoryImpl
    ): EmpleadosRepository

    @Binds
    @Singleton
    abstract fun bindHoraExtraRepository(
        impl: HoraExtraRepositoryImpl
    ): HoraExtraRepository
}