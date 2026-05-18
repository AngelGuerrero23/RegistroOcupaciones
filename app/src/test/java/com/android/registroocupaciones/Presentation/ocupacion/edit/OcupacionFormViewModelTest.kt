package com.android.registroocupaciones.Presentation.ocupacion.edit

import androidx.lifecycle.SavedStateHandle
import com.android.registroocupaciones.Presentacion.ocupaciones.edit.OcupacionFormUiEvent
import com.android.registroocupaciones.Presentacion.ocupaciones.edit.OcupacionFormViewModel
import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion
import com.android.registroocupaciones.domain.ocupacion.usecase.DeleteOcupacionUseCase
import com.android.registroocupaciones.domain.ocupacion.usecase.GetOcupacionUseCase
import com.android.registroocupaciones.domain.ocupacion.usecase.ObserveOcupacionUseCase
import com.android.registroocupaciones.domain.ocupacion.usecase.UpsertOcupacionUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class OcupacionFormViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var getOcupacion: GetOcupacionUseCase
    private lateinit var upsertOcupacion: UpsertOcupacionUseCase
    private lateinit var deleteOcupacion: DeleteOcupacionUseCase
    private lateinit var observeOcupacion: ObserveOcupacionUseCase

    @Before
    fun setUp(){
        Dispatchers.setMain(dispatcher)
        getOcupacion = mockk()
        upsertOcupacion = mockk()
        deleteOcupacion = mockk()
        observeOcupacion = mockk()

        every { observeOcupacion() } returns flowOf(emptyList())
    }


    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `Loading with a null or zero id sets a new state`()= runTest (dispatcher){
        val savedStateHandle = SavedStateHandle(mapOf("ocupacionId" to 0))
        val vm = model(savedStateHandle)

        vm.onEvent(OcupacionFormUiEvent.Load(0))
        runCurrent()

        val stateNew = vm.state.value
        assertTrue(stateNew.isNew)
        assertNull(stateNew.ocupacionId)
    }

    @Test
    fun `Load with id populates fields`()= runTest (dispatcher) {
        coEvery { getOcupacion(7) } returns Ocupacion(
            OcupacionId = 7,
            Descripcion = "Ingeniero en Software",
            Sueldo = 85000.00
        )

        val savedStateHandle = SavedStateHandle(mapOf("ocupacionId" to 7))
        val vm = model(savedStateHandle)

        vm.onEvent(OcupacionFormUiEvent.Load(7))
        runCurrent()

        val stateNew = vm.state.value
        assertFalse(stateNew.isNew)
        assertEquals(7, stateNew.ocupacionId)
        assertEquals("Ingeniero en Software",stateNew.descripcion)
        assertEquals("85000.00", stateNew.sueldo)
    }

    @Test
    fun `save with invalid inputs sets errors and does not save`()= runTest(dispatcher) {
        val savedStateHandle = SavedStateHandle(mapOf("ocupacionId" to 0))
        val vm = model(savedStateHandle)
        vm.onEvent(OcupacionFormUiEvent.DescripcionChanged(""))
        vm.onEvent(OcupacionFormUiEvent.SueldoChanged("abc"))

        vm.onEvent(OcupacionFormUiEvent.Save)
        runCurrent()

        val stateNew = vm.state.value
        assertNotNull(stateNew.descripcionError)
        assertNotNull(stateNew.sueldoError)
        assertFalse(stateNew.saved)
    }


    @Test
    fun `Save with valid inputs calls upsert and sets saved`()= runTest(dispatcher){
        coEvery { upsertOcupacion(any()) } returns Result.success(123)
        val savedStateHandle = SavedStateHandle(mapOf("OcupacionId" to 0))
        val vm = model(savedStateHandle)

        vm.onEvent(OcupacionFormUiEvent.DescripcionChanged("Ingeniero en Ciberseguridad"))
        vm.onEvent(OcupacionFormUiEvent.SueldoChanged("105000.00"))

        vm.onEvent(OcupacionFormUiEvent.Save)
        runCurrent()

        val stateNew = vm.state.value
        assertFalse(stateNew.isSaving)
        assertTrue(stateNew.saved)
        assertEquals(123, stateNew.ocupacionId)
    }

    @Test
    fun `delete when has id calls use case and marks deleted`()= runTest(dispatcher) {
        coEvery { deleteOcupacion(9) } returns Unit
        coEvery { getOcupacion(9) } returns Ocupacion(
            OcupacionId = 9,
            Descripcion = "Ingeniero Civil",
            Sueldo = 55000.00
        )

        val savedStateHandle = SavedStateHandle(mapOf("ocupacionId" to 9))
        val vm = model(savedStateHandle)

        vm.onEvent(OcupacionFormUiEvent.Load(9))
        runCurrent()

        vm.onEvent(OcupacionFormUiEvent.Delete)
        runCurrent()

        coVerify { deleteOcupacion(9) }
        val stateNew = vm.state.value
        assertFalse(stateNew.isDeleting)
        assertTrue(stateNew.deleted)
    }
    private fun model(savedStateHandle: SavedStateHandle): OcupacionFormViewModel {
        val vm = OcupacionFormViewModel(
            getOcupacion, upsertOcupacion,
            deleteOcupacion, observeOcupacion, savedStateHandle
        )
        return vm
    }



}