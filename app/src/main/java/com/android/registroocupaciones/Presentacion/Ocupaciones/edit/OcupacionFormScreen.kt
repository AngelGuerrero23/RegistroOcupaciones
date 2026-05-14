package com.android.registroocupaciones.Presentacion.Ocupaciones.edit


import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.test.espresso.base.Default
import com.android.registroocupaciones.domain.model.Ocupacion
import dagger.hilt.android.lifecycle.HiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OcupacionFormScreen(
    viewModel: OcupacionFormViewModel = hiltViewModel(),
    onBack: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved) {
        if(state.saved){
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

            OutlinedTextField(
                value = state.sueldo,
                onValueChange = {viewModel.onEvent(OcupacionFormUiEvent.SueldoChanged(it))},
                label = {Text("(RD$) Sueldo")},
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_time"),
                isError = state.sueldoError != null,
                supportingText = state.sueldoError?.let { {Text(it)} },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            if (state.sueldoError!=null)
            {
                Text(
                    state.sueldoError!!,
                    color= MaterialTheme.colorScheme.error
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
        }
    }
}