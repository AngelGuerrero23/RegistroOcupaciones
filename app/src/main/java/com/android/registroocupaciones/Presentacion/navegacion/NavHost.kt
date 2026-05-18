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
import com.android.registroocupaciones.Presentacion.empleado.edit.EmpleadoFormScreen
import com.android.registroocupaciones.Presentacion.empleado.list.EmpleadoListScreen
import com.android.registroocupaciones.Presentacion.ocupaciones.edit.OcupacionFormScreen
import com.android.registroocupaciones.Presentacion.ocupaciones.list.OcupacionListScreen

@Composable

fun RegistroNavHost(
    navController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues
){
    NavHost(
        navController = navController,
        startDestination = Screen.OcupacionList,
        modifier = Modifier.padding(innerPadding)
    ){
        composable<Screen.OcupacionList>{
            OcupacionListScreen(
                onAddOcupacion = {
                    navController.navigate(Screen.OcupacionForm())
                },
                onEditOcupacion = {id-> navController.navigate(Screen.OcupacionForm(ocupacionId = id))}
            )
        }
        composable<Screen.OcupacionForm>{
            OcupacionFormScreen(
                viewModel = hiltViewModel(),
                onBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<Screen.EmpleadoList>{
            EmpleadoListScreen(
                onAddEmpleado = {
                    navController.navigate(Screen.EmpleadoForm())
                },
                onEditEmpleado = {id-> navController.navigate(Screen.EmpleadoForm(empleadoId = id))
                }
            )
        }
        composable<Screen.EmpleadoForm>{
            EmpleadoFormScreen(
            viewModel= hiltViewModel(),
            onBack= { navController.navigateUp() }
            )
        }
    }
}
