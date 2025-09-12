package com.example.calculadorapersonal.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculadorapersonal.logic.*

@Composable
fun Calculadora() {
    var montoTexto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var saldoActual by remember { mutableStateOf(0.0) }
    var mensajeError by remember { mutableStateOf("") }

    fun agregarTransaccion(tipo: TipoTransaccion) {
        val error = validarTransaccion(montoTexto, descripcion)
        if (error != null) {
            mensajeError = error
            return
        }

        val monto = montoTexto.toDouble()
        saldoActual = aplicarTransaccion(saldoActual, monto, tipo)

        val nuevaTransaccion = Transaccion(monto, descripcion, tipo)
        HistorialTransacciones.agregar(nuevaTransaccion)

        montoTexto = ""
        descripcion = ""
        mensajeError = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Calculadora de Gastos", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4285F4))

        OutlinedTextField(
            value = montoTexto,
            onValueChange = { montoTexto = it; mensajeError = "" },
            label = { Text("Monto") },
            placeholder = { Text("0.00") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it; mensajeError = "" },
            label = { Text("Descripción") },
            placeholder = { Text("¿Qué compraste o recibiste?") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (mensajeError.isNotEmpty()) {
            Text(text = mensajeError, color = Color.Red, fontSize = 14.sp)
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Saldo", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "$${String.format("%.2f", saldoActual)}",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (saldoActual >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { agregarTransaccion(TipoTransaccion.INGRESO) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(8.dp)
            ) { Text("INGRESO", fontWeight = FontWeight.Bold) }

            Button(
                onClick = { agregarTransaccion(TipoTransaccion.GASTO) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
                shape = RoundedCornerShape(8.dp)
            ) { Text("GASTO", fontWeight = FontWeight.Bold) }

            Button(
                onClick = {
                    saldoActual = 0.0
                    montoTexto = ""
                    descripcion = ""
                    mensajeError = ""
                    HistorialTransacciones.limpiar()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = RoundedCornerShape(8.dp)
            ) { Text("LIMPIAR", fontWeight = FontWeight.Bold) }
        }

        Text("Historial de Transacciones", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(HistorialTransacciones.lista) { trans ->
                Item(trans)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculadora() {
    Calculadora()
}