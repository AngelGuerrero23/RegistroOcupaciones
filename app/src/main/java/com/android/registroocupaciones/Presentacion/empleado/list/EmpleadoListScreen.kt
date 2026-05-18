package com.android.registroocupaciones.Presentacion.empleado.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.registroempleados.domain.model.Empleados
import java.time.LocalDate

@Composable
fun EmpleadoListScreen(
    viewModel: EmpleadoListViewModel = hiltViewModel(),
    onAddEmpleado: () -> Unit,
    onEditEmpleado: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.navigateToCreate) {
        if(state.navigateToCreate){
            onAddEmpleado()
        }
    }

    LaunchedEffect(state.navigateToEditId) {
        state.navigateToEditId?.let {
            id-> onEditEmpleado(id)
        }
    }

    EmpleadoListBody(
        state = state,
        viewModel::onEvent,
        onAddEmpleado = onAddEmpleado,
        onEditClick = onEditEmpleado)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpleadoListBody(
    state: EmpleadoListUiState,
    onEvent: (EmpleadosListUiEvent) -> Unit,
    onAddEmpleado: () -> Unit,
    onEditClick: (Int) -> Unit
){
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.message){
        state.message?.let {
            message -> snackbarHostState.showSnackbar(message)
            onEvent(EmpleadosListUiEvent.ClearMessage)
        }
    }

    Scaffold(
        snackbarHost={ SnackbarHost(snackbarHostState) },
        floatingActionButton =
            {
                FloatingActionButton(
                    onClick = onAddEmpleado,
                    modifier = Modifier.testTag("fab_add")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar Empleado"
                    )
                }
            }
    ) {
        padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ){
            if(state.empleado.isEmpty())
            {
                Text(
                    text = "No hay empleados",
                    modifier= Modifier
                        .align(Alignment.Center)
                        .testTag("empty message"),
                    style = MaterialTheme.typography.bodyLarge
                )
            }else{
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = state.empleado,
                        key = {it.empleadosId}
                    )
                    {empleado ->
                        EmpleadoItem(
                            empleado = empleado,
                            onEdit = {onEditClick(empleado.empleadosId)},
                            onDelete = {onEvent(EmpleadosListUiEvent.Delete(empleado.empleadosId))}
                        )

                    }
                }
            }
        }
    }
}
@Composable
fun EmpleadoItem(
    empleado : Empleados,
    onEdit: () -> Unit,
    onDelete: () -> Unit
){
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth().clickable{onEdit()}
            .testTag("empleado_Item${empleado.empleadosId}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = empleado.nombres,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Sexo ${empleado.sexo} | Ingreso ${empleado.fechaIngreso}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "RD$ ${empleado.sueldo}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("btn_delete_${empleado.empleadosId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar empleado"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmpleadoListBodyPreview(){
    MaterialTheme{
        val state = EmpleadoListUiState(
            isLoading = false,
            empleado = listOf(
                Empleados(1, "Angel Guerrero", LocalDate.now(), "Masculino", 105000.00)
            )
        )
        EmpleadoListBody(state, {},{},{})
    }
}