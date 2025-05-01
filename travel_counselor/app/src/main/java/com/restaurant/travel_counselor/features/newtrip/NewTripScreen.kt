package com.restaurant.travel_counselor.features.newtrip

import RequiredTextField
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.restaurant.travel_counselor.database.AppDatabase
import com.restaurant.travel_counselor.entities.Trip
import com.restaurant.travel_counselor.shared.components.MyDatePickerField
import com.restaurant.travel_counselor.shared.components.RequiredNumberField
import com.restaurant.travel_counselor.shared.enums.AppRouter
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTripScreen(onNavigateTo: (String) -> Unit, tripId: Int?) {
    val context = LocalContext.current
    val tripDao = AppDatabase.getDatabase(context).tripDao()
    val factory = NewTripViewModelFactory(tripDao)
    val newTripViewModel: NewTripViewModel = viewModel(factory = factory)
    val uiState = newTripViewModel.uiState.collectAsState()

    LaunchedEffect(tripId) {
        if (tripId != null) {
            newTripViewModel.loadTripById(tripId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("New Trip") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RequiredTextField(
                label = "Destination",
                value = uiState.value.destination,
                onValueChange = newTripViewModel::onDestinationChange
            )

            TripTypeSelector(
                selectedType = uiState.value.tripType,
                onTypeChange = newTripViewModel::onTripTypeChange
            )

            val sdf = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
            val startDateMillis = remember(uiState.value.startDate) {
                try {
                    sdf.parse(uiState.value.startDate)?.time
                } catch (e: Exception) {
                    null
                }
            }

            MyDatePickerField(
                label = "Start Date",
                date = uiState.value.startDate,
                onDateChange = newTripViewModel::onStartDateChange,
                minDate = System.currentTimeMillis()
            )

            MyDatePickerField(
                label = "End Date",
                date = uiState.value.endDate,
                onDateChange = newTripViewModel::onEndDateChange,
                minDate = startDateMillis
            )

            RequiredNumberField(
                label = "Budget (R$)",
                value = uiState.value.budget,
                onValueChange = newTripViewModel::onBudgetChange
            )

            Button(
                onClick = {
                    newTripViewModel.saveTrip(
                        id = tripId ?: 0,
                        onSuccess = {
                            Toast.makeText(context, "Viagem salva com sucesso!", Toast.LENGTH_SHORT).show()
                            onNavigateTo(AppRouter.MENU.route)
                        },
                        onError = {
                            Toast.makeText(context, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            ) {
                Text("Salvar")
            }
        }
    }
}

@Composable
fun TripTypeSelector(selectedType: String, onTypeChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Trip Type:")
        RadioButtonWithLabel("Lazer", selectedType, onTypeChange)
        RadioButtonWithLabel("Trabalho", selectedType, onTypeChange)
    }
}

@Composable
fun RadioButtonWithLabel(label: String, selected: String, onSelect: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected == label,
            onClick = { onSelect(label) }
        )
        Text(text = label)
    }
}
