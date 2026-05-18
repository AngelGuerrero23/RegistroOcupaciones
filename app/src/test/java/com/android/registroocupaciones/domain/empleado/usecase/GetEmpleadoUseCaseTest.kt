package com.android.registroocupaciones.domain.empleado.usecase

import com.android.registroempleados.domain.model.Empleados
import com.android.registroempleados.domain.repository.EmpleadosRepository
import com.android.registroempleados.domain.usecase.GetEmpleadoUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class GetEmpleadoUseCaseTest {
    private lateinit var repository: EmpleadosRepository
    private lateinit var useCase: GetEmpleadoUseCase

    @Before
    fun setup(){
        repository = mockk()
        useCase = GetEmpleadoUseCase(repository)
    }

    @Test
    fun `returns empleado when repository finds it`() = runTest {
        coEvery { repository.getEmpleados(1) } returns Empleados(1, "Juan", LocalDate.now(), "Masculino", 23000.00)

        val result = useCase(1)

        assertEquals("Juan", result?.nombres)
    }

    @Test
    fun `returns null when repository returns null`() = runTest {
        coEvery { repository.getEmpleados(999) } returns null

        val result = useCase(999)

        assertEquals(null, result)
    }

}