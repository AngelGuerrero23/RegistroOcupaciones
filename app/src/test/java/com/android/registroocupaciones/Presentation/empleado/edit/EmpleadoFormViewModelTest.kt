package com.android.registroocupaciones.Presentation.empleado.edit

import androidx.lifecycle.SavedStateHandle
import com.android.registroempleados.domain.model.Empleados
import com.android.registroempleados.domain.usecase.DeleteEmpleadoUseCase
import com.android.registroempleados.domain.usecase.GetEmpleadoUseCase
import com.android.registroempleados.domain.usecase.UpsertEmpleadoUseCase
import com.android.registroocupaciones.Presentacion.empleado.edit.EmpleadoFormUiEvent
import com.android.registroocupaciones.Presentacion.empleado.edit.EmpleadoFormViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import java.time.LocalDate

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class EmpleadoFormViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var getEmpleado: GetEmpleadoUseCase
    private lateinit var upsertEmpleado: UpsertEmpleadoUseCase
    private lateinit var deleteEmpleado: DeleteEmpleadoUseCase

    @Before
    fun setUp(){
        Dispatchers.setMain(dispatcher)
        getEmpleado = mockk()
        upsertEmpleado = mockk()
        deleteEmpleado = mockk()
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `Load with null or zero id sets new state`()= runTest(dispatcher){
        val savedStateHandele = SavedStateHandle(mapOf("EmpleadoId" to 0))
        val vm = model(savedStateHandele)

        vm.onEvent(EmpleadoFormUiEvent.Load(0))
        runCurrent()

        val stateNew = vm.state.value
        assertTrue(stateNew.isNew)
        assertNull(stateNew.empleadoId)
    }

    @Test
    fun `Load with id populates the fiels`()=runTest(dispatcher) {
        val fecha = LocalDate.of(2023,5,10)
        coEvery { getEmpleado(7) } returns Empleados(
            7,
            "Miguel Angel",
            fecha,
            "Masculino",
            45000.00
        )

        val savedStateHandle = SavedStateHandle(mapOf("empleadoId" to 7))
        val vm = model(savedStateHandle)

        vm.onEvent(EmpleadoFormUiEvent.Load(7))
        runCurrent()

        val stateNew = vm.state.value
        assertFalse(stateNew.isNew)
        assertEquals(7, stateNew.empleadoId)
        assertEquals("Miguel Angel", stateNew.nombres)
        assertEquals(fecha, stateNew.fechaIngreso)
        assertEquals("Masculino", stateNew.sexo)
        assertEquals("45000.00", stateNew.sueldo)
    }

    @Test
    fun `save with invalid inputs sets errors and does not save`()= runTest(dispatcher){
        val savedStateHandle = SavedStateHandle(mapOf("empleadoId" to 0))
        val vm = model(savedStateHandle)

        vm.onEvent(EmpleadoFormUiEvent.NombresChanged(""))
        vm.onEvent(EmpleadoFormUiEvent.sueldoChanged("abc"))
        vm.onEvent(EmpleadoFormUiEvent.sexoChanged(""))

        vm.onEvent(EmpleadoFormUiEvent.Save)
        runCurrent()

        val stateNew = vm.state.value
        assertNotNull(stateNew.nombresError)
        assertNotNull(stateNew.sueldoError)
        assertNotNull(stateNew.sexoError)
        assertFalse(stateNew.saved)
    }

    @Test
    fun `save with valid inputs calls upsert and sets saved`()=runTest (dispatcher){
        coEvery { upsertEmpleado(any()) } returns Result.success(123)
        val savedStateHandle = SavedStateHandle(mapOf("empleadoId" to 0))
        val vm = model(savedStateHandle)

        vm.onEvent(EmpleadoFormUiEvent.NombresChanged("Guillermina"))
        vm.onEvent(EmpleadoFormUiEvent.sueldoChanged("62000.0"))
        vm.onEvent(EmpleadoFormUiEvent.sexoChanged("Femenino"))
        vm.onEvent(EmpleadoFormUiEvent.fechaChanged(LocalDate.now()))

        vm.onEvent(EmpleadoFormUiEvent.Save)
        runCurrent()

        val s = vm.state.value
        assertFalse(s.isSaving)
        assertTrue(s.saved)
        assertEquals(123, s.empleadoId)
    }

    @Test
    fun `delete when has id calls use case and marks deleted`()=runTest(dispatcher) {
        coEvery { deleteEmpleado(9) } returns Unit
        coEvery { getEmpleado(9) } returns Empleados(
            9,
            "Usuario",
            LocalDate.now(),
            "Masculino",
            10000.0
        )

        val savedStateHandle = SavedStateHandle(mapOf("empleadoId" to 9))
        val vm = model(savedStateHandle)

        vm.onEvent(EmpleadoFormUiEvent.Load(9))
        runCurrent()

        vm.onEvent(EmpleadoFormUiEvent.Delete)
        runCurrent()

        coVerify { deleteEmpleado(9) }
        val stateNew = vm.state.value
        assertFalse(stateNew.isDeleting)
        assertTrue(stateNew.deleted)
    }

    private fun model(savedStateHandle: SavedStateHandle): EmpleadoFormViewModel {
        val vm =
            EmpleadoFormViewModel(getEmpleado, upsertEmpleado, deleteEmpleado, savedStateHandle)
        return vm
    }

}