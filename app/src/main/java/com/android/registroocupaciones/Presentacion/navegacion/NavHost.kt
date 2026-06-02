package com.android.registroocupaciones.Presentacion.navegacion

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.registroocupaciones.Presentacion.empleado.adaptive.EmpleadosAdaptiveScreen
import com.android.registroocupaciones.Presentacion.empleado.edit.EmpleadoFormScreen
import com.android.registroocupaciones.Presentacion.empleado.list.EmpleadoListScreen
import com.android.registroocupaciones.Presentacion.horaextra.adaptive.HoraExtraAdaptiveScreen
import com.android.registroocupaciones.Presentacion.horaextra.edit.HoraExtraFormScreen
import com.android.registroocupaciones.Presentacion.horaextra.list.HoraExtraListScreen
import com.android.registroocupaciones.Presentacion.ocupaciones.adaptive.OcupacionesAdaptiveScreen
import com.android.registroocupaciones.Presentacion.ocupaciones.edit.OcupacionFormScreen
import com.android.registroocupaciones.Presentacion.ocupaciones.list.OcupacionListScreen

@Composable

fun RegistroNavHost(
    navController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues = PaddingValues()
){
    NavHost(
        navController = navController,
        startDestination = Screen.OcupacionList,
        modifier = Modifier.padding(innerPadding)
    ){
        composable<Screen.OcupacionList>{
            OcupacionesAdaptiveScreen()
        }

        composable<Screen.EmpleadoList>{
            EmpleadosAdaptiveScreen()
        }

        composable<Screen.HoraExtraList> {
            HoraExtraAdaptiveScreen()
        }
    }
}
