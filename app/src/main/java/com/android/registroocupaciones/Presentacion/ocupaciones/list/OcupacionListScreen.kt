package com.android.registroocupaciones.Presentacion.ocupaciones.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.registroocupaciones.domain.ocupacion.model.Ocupacion

@Composable
fun OcupacionListScreen(
    viewModel: OcupacionListViewModel = hiltViewModel(),
    onAddOcupacion: () -> Unit,
    onEditOcupacion: (Int) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    OcupacionListBody(
        state = state,
        viewModel::onEvent,
        onAddOcupacion = onAddOcupacion,
        onEditClick = onEditOcupacion)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OcupacionListBody(
    state: OcupacionListUiState,
    onEvent: (OcupacionesListUiEvent) -> Unit,
    onAddOcupacion:() -> Unit,
    onEditClick:(Int) -> Unit
){
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.message) {
        state.message?.let{
            message -> snackbarHostState.showSnackbar(message)
            onEvent(OcupacionesListUiEvent.ClearMessage)
        }
    }

    Scaffold(
        snackbarHost={ SnackbarHost(snackbarHostState) },
        floatingActionButton =
            {
                FloatingActionButton(
                    onClick = onAddOcupacion,
                    modifier= Modifier.testTag("fab_add")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar Ocupación"
                    )
                }
            }
    ) {
        padding ->
        Box(
            modifier= Modifier
                .padding(padding)
                .fillMaxSize()
        ){
            if(state.isLoading)
            {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align ( Alignment.Center)
                        .testTag("loading...")
                )
            }else{
                if(state.ocupacion.isEmpty())
                {
                    Text(
                        text="No hay Ocupaciones",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("empty_messaage"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }else{
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = state.ocupacion,
                            key = {it.OcupacionId}
                        ){ocupacion ->
                            OcupacionItem(
                                ocupacion = ocupacion,
                                onEdit = {onEditClick(ocupacion.OcupacionId)},
                                onDelete = { onEvent(OcupacionesListUiEvent.Delete(ocupacion.OcupacionId)) }
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun OcupacionItem(
    ocupacion: Ocupacion,
    onEdit: ()-> Unit,
    onDelete: () -> Unit
){
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth().clickable{onEdit()}
            .testTag("ocupacion_Item${ocupacion.OcupacionId}")
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text= ocupacion.Descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "RD$ ${ocupacion.Sueldo}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("btn_delete_${ocupacion.OcupacionId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar ocupación"
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun OcupacionListBodyPreview(){
    MaterialTheme{
        val state = OcupacionListUiState(
            isLoading = false,
            ocupacion = listOf(
                Ocupacion(OcupacionId =1, Descripcion ="Ingeniero en Sistemas", Sueldo =50000.00)
            )
        )
        OcupacionListBody(state, {}, {},{})
    }
}

