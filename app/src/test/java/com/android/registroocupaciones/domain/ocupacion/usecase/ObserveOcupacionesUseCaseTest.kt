package com.android.registroocupaciones.domain.ocupacion.usecase

import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import com.android.registroocupaciones.domain.ocupacion.repository.OcupacionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ObserveOcupacionesUseCaseTest {

    private lateinit var useCase: ObserveOcupacionUseCase
    private lateinit var repository: OcupacionRepository


    @Before
    fun setup(){
        repository = mockk()
        useCase = ObserveOcupacionUseCase(repository)
    }

    @Test
    fun `invoke call repository and return a stream of occupations`()= runTest {
        val listaEsperada = listOf(Ocupacion(1,"Granjero", 70000.00),
            Ocupacion(2,"Carnicero", 45000.00)
        )
        coEvery { repository.observeOcupacion() } returns flowOf(listaEsperada)

        val result = useCase().first()

        assertEquals(listaEsperada, result)
        coVerify(exactly = 1){repository.observeOcupacion()}
    }

    @Test
    fun `invoke return an empty stream if no records exist`()=runTest {
        val listaVacia = emptyList<Ocupacion>()
        coEvery { repository.observeOcupacion() } returns flowOf(listaVacia)

        val result = useCase().first()

        assertEquals(listaVacia, result)
        coVerify (exactly = 1){repository.observeOcupacion()}
    }
}