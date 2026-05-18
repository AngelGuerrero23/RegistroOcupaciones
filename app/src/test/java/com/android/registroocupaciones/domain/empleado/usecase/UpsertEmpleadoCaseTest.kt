package com.android.registroocupaciones.domain.empleado.usecase

import com.android.registroempleados.domain.model.Empleados
import com.android.registroempleados.domain.repository.EmpleadosRepository
import com.android.registroempleados.domain.usecase.UpsertEmpleadoUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class UpsertEmpleadoCaseTest {
    private lateinit var repository: EmpleadosRepository
    private lateinit var useCase: UpsertEmpleadoUseCase

    @Before
    fun setup(){
        repository = mockk()
        coEvery { repository.observeEmpleados() } returns flowOf(emptyList())
        useCase = UpsertEmpleadoUseCase(repository)
    }

    @Test
    fun `fails when name is blanck`() = runTest {
        val empleado = Empleados(0, "", LocalDate.now(), "Masculino", 23000.00)

        val result = useCase(empleado)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `invoke save employee with invalid dates`() = runTest {
        val empleado = Empleados(0,"Miguel",LocalDate.now(), "Masculino", 30000.00 )
        coEvery { repository.upsert(any()) } returns 1

        val result = useCase(empleado)

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull())
        coVerify { repository.upsert(empleado) }
    }

    @Test
    fun `invoke failure short name`() = runTest {
        val empleado = Empleados(0,"Ma", LocalDate.now(), "Masculino", 25000.00)

        val result = useCase(empleado)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `invoke name contains invalid characters`() = runTest {
        val empleado = Empleados(0, "@a2345", LocalDate.now(), "Masculino", 25000.00)

        val result = useCase(empleado)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `invoke salary validation failure`() = runTest {
        val empleado = Empleados(0, "Jose", LocalDate.now(), "Masculino", -2500.00)

        val result = useCase(empleado)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `invoke gender field is empty`() = runTest {
        val empleado = Empleados(0, "Minguito", LocalDate.now(), "", 25000.00)

        val result = useCase(empleado)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun `invoke date cannot be in the future`() = runTest {
        val empleado = Empleados(0, "Miguelito", LocalDate.now().plusYears(2), "Masculino", 25000.00)

        val result = useCase(empleado)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }
}