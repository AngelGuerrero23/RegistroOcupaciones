package com.android.registroocupaciones.Presentation.empleado.list

import androidx.compose.material3.ExperimentalMaterial3Api
import com.android.registroempleados.domain.model.Empleados
import com.android.registroempleados.domain.usecase.DeleteEmpleadoUseCase
import com.android.registroempleados.domain.usecase.ObserveEmpleadoUseCase
import com.android.registroocupaciones.Presentacion.empleado.list.EmpleadoListViewModel
import com.android.registroocupaciones.Presentacion.empleado.list.EmpleadosListUiEvent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class EmpleadoListViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var observeEmpleado: ObserveEmpleadoUseCase
    private lateinit var deleteEmpleado: DeleteEmpleadoUseCase

    @Before
    fun setUp(){
        Dispatchers.setMain(dispatcher)
        observeEmpleado = mockk()
        deleteEmpleado = mockk()
    }

    @Test
    fun `delete calls use case and show messagge`()= runTest (dispatcher){
        val shared = MutableSharedFlow<List<Empleados>>(replay = 1)
        shared.emit(emptyList())
        every {observeEmpleado()} returns shared
        coEvery { deleteEmpleado(5) } returns Unit

        val vm = model()
        runCurrent()

        vm.onEvent(EmpleadosListUiEvent.Delete(5))
        runCurrent()

        coVerify {deleteEmpleado(5) }
        assertEquals("Eliminado", vm.state.value.message)
    }

    @Test
    fun `navigation flags change as expected `()= runTest(dispatcher){
        val shared = MutableSharedFlow<List<Empleados>>(replay = 1)
        shared.emit(emptyList())
        every{observeEmpleado()} returns shared
        val vm = model()
        runCurrent()

        vm.onEvent(EmpleadosListUiEvent.CreateNew)
        assertTrue(vm.state.value.navigateToCreate)

        vm.onEvent(EmpleadosListUiEvent.Edit(10))
        assertEquals(10, vm.state.value.navigateToEditId)
    }

    private fun model(): EmpleadoListViewModel {
        val vm = EmpleadoListViewModel(observeEmpleado, deleteEmpleado)
        return vm
    }
}