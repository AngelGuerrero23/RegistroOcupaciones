package com.android.registroocupaciones.di

import android.content.Context
import androidx.room.Room
import com.android.registroocupaciones.data.database.OcupacionDb
import com.android.registroocupaciones.data.local.OcupacionDao
import com.android.registroocupaciones.data.repository.OcupacionRepositoryImpl
import com.android.registroocupaciones.domain.repository.OcupacionRepository
import com.android.registroocupaciones.domain.usecase.*
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
    fun provideOcupacionDb(@ApplicationContext context: Context): OcupacionDb {
        return Room.databaseBuilder(
            context,
            OcupacionDb::class.java,
            "ocupaciones_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideOcupacionDao(db: OcupacionDb): OcupacionDao {
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
