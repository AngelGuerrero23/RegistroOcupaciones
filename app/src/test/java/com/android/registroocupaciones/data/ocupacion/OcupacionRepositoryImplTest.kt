package com.android.registroocupaciones.data.ocupacion

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.registroocupaciones.data.ocupacion.local.OcupacionDao
import com.android.registroocupaciones.data.ocupacion.local.OcupacionEntity
import com.android.registroocupaciones.data.ocupacion.repository.OcupacionRepositoryImpl
import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class OcupacionRepositoryImplTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: OcupacionRepositoryImpl
    private lateinit var dao: OcupacionDao

    @Before
    fun setup(){
        dao = mockk(relaxed = true)
        repository = OcupacionRepositoryImpl(dao)
    }

    @Test
    fun `upsert_guardar save the ocupation successfully`()= runTest {
        val ocupacion = Ocupacion(
            0,
            "Ingeniero en Sistemas",
            75000.00
        )
        val ocupacionSlot = slot<OcupacionEntity>()
        coEvery { dao.upsert(capture(ocupacionSlot)) } just Runs

        val result = repository.upsert(ocupacion)

        assertEquals(0, result)
        coVerify { dao.upsert(any()) }
        assertEquals(ocupacion.Descripcion, ocupacionSlot.captured.Descripcion)
        assertEquals(ocupacion.Sueldo, ocupacionSlot.captured.Sueldo)
    }

    @Test
    fun `upsert update occupation successfully`()= runTest {
        val ocupacion = Ocupacion(
            1,
            "Ocupacion Actualizada",
            30000.00)
        coEvery {dao.upsert(any())} just Runs

        val result = repository.upsert(ocupacion)

        assertEquals(1, result)
        coVerify { dao.upsert(any()) }
    }

    @Test
    fun `occupation delete successfully`()= runTest {
        val ocupacionId = 1
        coEvery { dao.deleteById(ocupacionId) } just Runs

        repository.delete(ocupacionId)
        coVerify { dao.deleteById(ocupacionId) }
    }

    @Test
    fun `observe occupation returns a flow of occupations`() = runTest {
        val entities = listOf(
            OcupacionEntity(1,"Cirujano", 89000.00),
            OcupacionEntity(2,"Enfermera",45000.00)
        )
        every { dao.observeAll() } returns flowOf(entities)
        val result = repository.observeOcupacion().first()

        assertEquals(2, result.size)
        assertEquals("Cirujano", result[0].Descripcion)
        assertEquals("Enfermera", result[1].Descripcion)
    }

    @Test
    fun `getOcupacion returns occupation by id`()= runTest {
        val entity = OcupacionEntity(1, "Mecanico", 25000.00)
        coEvery { dao.getById(1) } returns entity

        val result = repository.getOcupacion(1)

        assertNotNull(result)
        assertEquals("Mecanico", result?.Descripcion)
        assertEquals(25000.00, result?.Sueldo)
    }
}