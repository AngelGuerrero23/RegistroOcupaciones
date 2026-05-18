package com.android.registroocupaciones.Presentation.ocupacion.list

import com.android.registroocupaciones.Presentacion.ocupaciones.list.OcupacionListViewModel
import com.android.registroocupaciones.Presentacion.ocupaciones.list.OcupacionesListUiEvent
import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import com.android.registroocupaciones.domain.ocupacion.usecase.DeleteOcupacionUseCase
import com.android.registroocupaciones.domain.ocupacion.usecase.ObserveOcupacionUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OcupacionListViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var observeOcupacion: ObserveOcupacionUseCase
    private lateinit var deleleOcupacion: DeleteOcupacionUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        observeOcupacion = mockk()
        deleleOcupacion = mockk()
    }

    @Test
    fun `delete call the use case and display the message`() = runTest(dispatcher) {
        val shared = MutableSharedFlow<List<Ocupacion>>(replay = 1)
        shared.emit(emptyList())
        every { observeOcupacion() } returns shared
        coEvery { deleleOcupacion(5) } returns Unit

        val vm = OcupacionListViewModel(observeOcupacion, deleleOcupacion)
        runCurrent()

        vm.onEvent(OcupacionesListUiEvent.Delete(5))
        runCurrent()

        coVerify { deleleOcupacion(5) }
        assertEquals("Eliminado", vm.state.value.message)
    }

    @Test
    fun `navigation flags changed as expected`()=runTest(dispatcher) {
        val shared = MutableSharedFlow<List<Ocupacion>>(replay = 1)
        shared.emit(emptyList())
        every { observeOcupacion() } returns shared
        val vm= OcupacionListViewModel(observeOcupacion, deleleOcupacion)
        runCurrent()

        vm.onEvent(OcupacionesListUiEvent.CreateNew)
        assertTrue(vm.state.value.navigateToCreate)

        vm.onEvent(OcupacionesListUiEvent.Edit(10))
        assertEquals(10, vm.state.value.navigateToEditId)
    }
}