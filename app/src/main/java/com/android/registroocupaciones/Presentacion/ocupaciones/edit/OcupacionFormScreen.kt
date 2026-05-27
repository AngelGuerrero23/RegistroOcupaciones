package com.android.registroocupaciones.Presentacion.ocupaciones.edit


import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OcupacionFormScreen(
    viewModel: OcupacionFormViewModel = hiltViewModel(),
    onBack: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved, state.deleted) {
        if(state.saved || state.deleted){
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if(state.isNew) "Nueva Ocupación" else "Editar Ocupacion")},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atras")
                    }
                }
            )
        }
    ) {padding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            OutlinedTextField(
                value = state.descripcion,
                onValueChange = {viewModel.onEvent(OcupacionFormUiEvent.DescripcionChanged(it))},
                label ={Text("Descripcion")},
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_descripcion"),
                isError = state.descripcionError != null,
                supportingText = state.descripcionError?.let { {Text(it)} },
                singleLine = false,
                minLines = 3,
                maxLines = 5
            )
            if(state.descripcionError !=null)
            {
                Text(
                    state.descripcionError!!,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Row(
                Modifier.fillMaxWidth()
                    .toggleable(
                        value = state.esPuestoDireccion,
                        onValueChange ={ isCheked ->
                            viewModel.onEvent(OcupacionFormUiEvent.esPuestoDireccionChanged(isCheked))
                        },
                        role = androidx.compose.ui.semantics.Role.Checkbox
                    )
                    .padding(vertical = 8.dp)
                    .testTag("checkbox_puesto_direccion"),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.esPuestoDireccion,
                    onCheckedChange = null
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = "Es un puesto direccion?",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Button(
                onClick = {viewModel.onEvent(OcupacionFormUiEvent.Save)},
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_save"),
                enabled = !state.isSaving
            ) {
                if(state.isSaving){
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }else{
                    Text("Guardar")
                }
            }

            if (!state.isNew){
                Button(
                    onClick = {
                        viewModel.onEvent(OcupacionFormUiEvent.Delete)
                    },
                    modifier = Modifier.fillMaxWidth()
                        .testTag("btn_delete"),
                    enabled = !state.isDeleting,
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.error,
                        MaterialTheme.colorScheme.onError
                    )
                ) {
                    if (state.isDeleting){
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onError,
                            strokeWidth = 2.dp
                        )
                    }else{
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}