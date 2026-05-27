package com.android.registroocupaciones.data.empleado

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.registroempleados.data.local.EmpleadosDao
import com.android.registroempleados.data.local.HorasExtrasEntity
import com.android.registroempleados.data.repository.EmpleadosRepositoryImpl
import com.android.registroempleados.domain.model.Empleados
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate


@ExperimentalCoroutinesApi
class EmpleadoRepositoryImplTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: EmpleadosRepositoryImpl
    private lateinit var dao: EmpleadosDao

    @Before
    fun setup(){
        dao = mockk(relaxed = true)
        repository = EmpleadosRepositoryImpl(dao)
    }

    @Test
    fun `upsert employee saved successfully`() = runTest {
        val fechaActual = LocalDate.now()
        val empleado = Empleados(
            0,
            "Maria Lopez",
            fechaActual,
            "Femenino",
            49000.00
        )
        val empleadoSlot = slot<HorasExtrasEntity>()
        coEvery{dao.upsert(capture(empleadoSlot))} just Runs

        val result = repository.upsert(empleado)

        assertEquals(0, result)
        coEvery { dao.upsert((any())) }
        assertEquals(empleado.nombres, empleadoSlot.captured.Fecha)
        assertEquals(empleado.sueldo, empleadoSlot.captured.Recargo, 0.0)
        assertEquals(empleado.fechaIngreso, empleadoSlot.captured.FechaIngreso)
        assertEquals(empleado.sexo, empleadoSlot.captured.Tipo)
    }

    @Test
    fun `upsert update employee successfully`()=runTest{
        val empleado = Empleados(
            1,
            "Empleado Actualizado",
             LocalDate.now(),
            "Femenino",
            25000.00
        )
        coEvery { dao.upsert(any()) } just Runs

        val result = repository.upsert(empleado)

        assertEquals(1, result)
        coVerify { dao.upsert(any()) }
    }

    @Test
    fun `delete employee successfully`() = runTest {
        val empleadoId = 1
        coEvery { dao.deleteById(empleadoId) } just Runs

        repository.delete(empleadoId)
        coVerify { dao.deleteById(empleadoId) }
    }

    @Test
    fun `observe the employee method returns a flow of employees recors`()= runTest {
        val entities = listOf(
            HorasExtrasEntity(1, "Miguel", Tipo = "Masculino", Recargo = 75000.00),
            HorasExtrasEntity(2, "Josefina", Tipo = "Femenino", Recargo = 65000.00)
        )
        every { dao.observeAll() } returns flowOf(entities)

        val result = repository.observeEmpleados().first()

        assertEquals(2, result.size)
        assertEquals("Miguel", result[0].nombres)
        assertEquals("Josefina", result[1].nombres)
    }

    @Test
    fun `getEmpleado returns employee by id`()= runTest {
        val entity = HorasExtrasEntity(1, "Ana", Tipo = "Femenino", Recargo = 35000.00)
        coEvery { dao.getById(1) } returns entity

        val result = repository.getEmpleados(1)

        assertNotNull(result)
        assertEquals("Ana", result?.nombres)
        assertEquals(35000.00, result?.sueldo)
        assertEquals("Femenino", result?.sexo)
    }
}