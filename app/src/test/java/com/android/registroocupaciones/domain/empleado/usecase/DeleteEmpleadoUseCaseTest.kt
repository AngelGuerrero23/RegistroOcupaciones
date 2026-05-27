package com.android.registroocupaciones.domain.empleado.usecase

import com.android.registroocupaciones.domain.empleado.repository.EmpleadosRepository
import com.android.registroempleados.domain.usecase.DeleteEmpleadoUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DeleteEmpleadoUseCaseTest {
    private lateinit var repository : EmpleadosRepository
    private lateinit var useCase: DeleteEmpleadoUseCase

    @Before
    fun setup(){
        repository = mockk(relaxed = true)
        useCase = DeleteEmpleadoUseCase(repository)
    }

    @Test
    fun `calls repository delete with id`() = runTest {
        coEvery { repository.delete(5) } just Runs

        useCase(5)

        coVerify { repository.delete(5) }
    }
}