package com.android.registroocupaciones.Presentacion.empleado.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpleadoFormScreen(
    viewModel: EmpleadoFormViewModel = hiltViewModel(),
    onBack: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    var expandedSexo by remember { mutableStateOf(false) }
    var ocupacionExpanded by remember { mutableStateOf(false) }
    var frecuenciaPagoExpanded by remember { mutableStateOf(false) }

    val opcionesSexo = listOf("Masculino", "Femenino")

    LaunchedEffect(state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (state.isNew)
                            "Nuevo empleado"
                        else
                            "Editar empleado"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = ocupacionExpanded,
                onExpandedChange = {
                    ocupacionExpanded = !ocupacionExpanded
                }
            ) {
                OutlinedTextField(
                    value = state.descripcionOcupacion,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Ocupación") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = ocupacionExpanded
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .testTag("input_ocupacion"),
                    isError = state.ocupacionError != null,
                    supportingText = {
                        state.ocupacionError?.let {
                            Text(it)
                        }
                    }
                )
                ExposedDropdownMenu(
                    expanded = ocupacionExpanded,
                    onDismissRequest = {
                        ocupacionExpanded = false
                    }
                ) {
                    if (state.ocupaciones.isEmpty()) {

                        DropdownMenuItem(
                            text = {
                                Text("Cargando ocupaciones...")
                            },
                            onClick = {},
                            enabled = false
                        )
                    } else {
                        state.ocupaciones.forEach { ocupacion ->
                            DropdownMenuItem(
                                text = {
                                    Text(ocupacion.Descripcion)
                                },
                                onClick = {
                                    viewModel.onEvent(
                                        EmpleadoFormUiEvent.OcupacionChanged(
                                            ocupacion.OcupacionId.toString()
                                        )
                                    )
                                    viewModel.onEvent(
                                        EmpleadoFormUiEvent.DescripcionOcupacionChanged(
                                            ocupacion.Descripcion
                                        )
                                    )
                                    ocupacionExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            OutlinedTextField(
                value = state.nombres,
                onValueChange = {
                    viewModel.onEvent(
                        EmpleadoFormUiEvent.NombresChanged(it)
                    )
                },
                label = {
                    Text("Nombres")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_nombres"),
                isError = state.nombresError != null,
                supportingText = {
                    state.nombresError?.let {
                        Text(it)
                    }
                },
                singleLine = true
            )
            ExposedDropdownMenuBox(
                expanded = expandedSexo,
                onExpandedChange = {
                    expandedSexo = !expandedSexo
                }
            ) {
                OutlinedTextField(
                    value = state.sexo,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text("Sexo")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandedSexo
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .testTag("input_sexo"),
                    isError = state.sexoError != null,
                    supportingText = {
                        state.sexoError?.let {
                            Text(it)
                        }
                    }
                )

                ExposedDropdownMenu(
                    expanded = expandedSexo,
                    onDismissRequest = {
                        expandedSexo = false
                    }
                ) {
                    opcionesSexo.forEach { opcion ->
                        DropdownMenuItem(
                            text = {
                                Text(opcion)
                            },
                            onClick = {
                                viewModel.onEvent(
                                    EmpleadoFormUiEvent.SexoChanged(opcion)
                                )
                                expandedSexo = false
                            }
                        )
                    }
                }
            }
            OutlinedTextField(
                value = state.fechaIngreso.toString(),
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Fecha de ingreso")
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showDatePicker = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Seleccionar fecha"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showDatePicker = true
                    }
                    .testTag("input_fecha"),
                isError = state.fechaIngresoError != null,
                supportingText = {
                    state.fechaIngresoError?.let {
                        Text(it)
                    }
                }
            )
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = {
                        showDatePicker = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    val date = Instant
                                        .ofEpochMilli(millis)
                                        .atZone(ZoneId.of("UTC"))
                                        .toLocalDate()
                                    viewModel.onEvent(
                                        EmpleadoFormUiEvent.FechaIngresoChanged(date)
                                    )
                                }
                                showDatePicker = false
                            }
                        ) {
                            Text("Aceptar")
                        }
                    },
                    dismissButton = {

                        TextButton(
                            onClick = {
                                showDatePicker = false
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
            OutlinedTextField(
                value = state.sueldo,
                onValueChange = {
                    viewModel.onEvent(
                        EmpleadoFormUiEvent.SueldoChanged(it)
                    )
                },
                label = {
                    Text("Sueldo")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_sueldo"),
                isError = state.sueldoError != null,
                supportingText = {
                    state.sueldoError?.let {
                        Text(it)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                singleLine = true
            )
            Button(
                onClick = {
                    viewModel.onEvent(
                        EmpleadoFormUiEvent.Save
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_save"),
                enabled = !state.isSaving
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {

                    Text("Guardar")
                }
            }
            if (!state.isNew) {

                Button(
                    onClick = {
                        viewModel.onEvent(
                            EmpleadoFormUiEvent.Delete
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("btn_delete"),
                    enabled = !state.isDeleting,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    if (state.isDeleting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onError,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}