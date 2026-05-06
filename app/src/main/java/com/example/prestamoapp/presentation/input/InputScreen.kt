package com.example.prestamoapp.presentation.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prestamoapp.calcularPrestamo

@Composable
fun InputScreen(navController: NavController) {

    var monto by remember { mutableStateOf("") }
    var plazo by remember { mutableStateOf("") }
    var tasa by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    val montoMaximo = 1_000_000.0
    val plazoMaximo = 50
    val tasaMaxima = 100.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Calculadora de Préstamo",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = monto,
            onValueChange = { nuevoValor ->
                if (nuevoValor.matches(Regex("^\\d*\\.?\\d*$"))) {
                    monto = nuevoValor
                }
            },
            label = { Text("Monto del préstamo") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = plazo,
            onValueChange = { nuevoValor ->
                if (nuevoValor.all { it.isDigit() }) {
                    plazo = nuevoValor
                }
            },
            label = { Text("Plazo en años") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = tasa,
            onValueChange = { nuevoValor ->
                if (nuevoValor.matches(Regex("^\\d*\\.?\\d*$"))) {
                    tasa = nuevoValor
                }
            },
            label = { Text("Tasa anual (%)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (monto.isBlank() || plazo.isBlank() || tasa.isBlank()) {
                    error = "No se permiten campos vacíos"
                    return@Button
                }

                val montoDouble = monto.toDoubleOrNull()
                val plazoInt = plazo.toIntOrNull()
                val tasaDouble = tasa.toDoubleOrNull()

                if (montoDouble == null || plazoInt == null || tasaDouble == null) {
                    error = "Todos los valores deben ser numéricos"
                    return@Button
                }

                if (montoDouble <= 0 || montoDouble > montoMaximo) {
                    error = "El monto debe estar entre 1 y 1,000,000"
                    return@Button
                }

                if (plazoInt <= 0 || plazoInt > plazoMaximo) {
                    error = "El plazo debe estar entre 1 y 50 años"
                    return@Button
                }

                if (tasaDouble < 0 || tasaDouble > tasaMaxima) {
                    error = "La tasa anual debe estar entre 0% y 100%"
                    return@Button
                }

                val meses = plazoInt * 12

                val (cuota, _, interes) = calcularPrestamo(
                    monto = montoDouble,
                    tasaAnual = tasaDouble,
                    meses = meses
                )

                error = ""

                navController.navigate(
                    "result/$cuota/$interes/$montoDouble"
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }
    }
}