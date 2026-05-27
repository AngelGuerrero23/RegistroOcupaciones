package com.android.registroocupaciones.domain.empleado.usecase

import com.android.registroempleados.domain.model.Empleados
import com.android.registroocupaciones.domain.empleado.repository.EmpleadosRepository
import com.android.registroempleados.domain.usecase.ObserveEmpleadoUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class ObserveEmpleadoUseCaseTest {
    private lateinit var repository: EmpleadosRepository
    private lateinit var useCase: ObserveEmpleadoUseCase

    @Before
    fun setup(){
        repository = mockk()
        useCase = ObserveEmpleadoUseCase(repository)
    }

    @Test
    fun `emits lists from repository`() = runTest {
        val shared = listOf(
            Empleados( 1, "Juan", LocalDate.now(), "Masculino", 23000.00),
            Empleados(2, "Josefina", LocalDate.now(), "Femenino", 20000.00)
        )
        coEvery { repository.observeEmpleados() } returns flowOf(shared)

        val result = useCase().first()

        assertEquals(shared, result)
        coVerify(exactly = 1){repository.observeEmpleados()
        }

    }

    @Test
    fun `emits null lists with not register`() = runTest {
        val listaVacia = emptyList<Empleados>()
        coEvery { repository.observeEmpleados() } returns flowOf(listaVacia)

        val result = useCase().first()

        assertEquals(listaVacia, result)
        coVerify(exactly = 1){repository.observeEmpleados()}

    }
}