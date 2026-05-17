package com.android.registroocupaciones.Presentacion.ocupaciones.navegacion

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.registroocupaciones.Presentacion.ocupaciones.edit.OcupacionFormScreen
import com.android.registroocupaciones.Presentacion.ocupaciones.list.OcupacionListScreen

@Composable

fun OcupacionNavHost(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = Screen.OcupacionList
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
    }
}
