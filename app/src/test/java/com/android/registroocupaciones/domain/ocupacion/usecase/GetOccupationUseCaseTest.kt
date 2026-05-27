package com.android.registroocupaciones.domain.ocupacion.usecase

import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import com.android.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetOccupationUseCaseTest {
    private lateinit var useCase: GetOcupacionUseCase
    private lateinit var repository: OcupacionRepository

    @Before
    fun setup(){
        repository = mockk()
        useCase = GetOcupacionUseCase(repository)
    }

    @Test
    fun `invoke call repository and return ocupation by id`() = runTest {
        val ocupacionId = 1
        val ocupacionEsperada = Ocupacion(ocupacionId,"Ingeniero en Sistemas", 105000.00)

        coEvery { repository.getOcupacion(ocupacionId) } returns ocupacionEsperada

        val result = useCase(ocupacionId)

        assertEquals(ocupacionEsperada, result)
        coVerify (exactly = 1){repository.getOcupacion((ocupacionId))}
    }

    @Test
    fun `invoke return null if id doesn't exist`()=runTest {
        val idInexistente =99

        coEvery { repository.getOcupacion(idInexistente) } returns null

        val result = useCase(idInexistente)

        assertNull(result)
        coVerify (exactly = 1){
            repository.getOcupacion(idInexistente)
        }
    }
}