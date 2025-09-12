package com.example.calculadorapersonal.logic

import java.text.SimpleDateFormat
import java.util.*

enum class TipoTransaccion { INGRESO, GASTO }

data class Transaccion(
    val monto: Double,
    val descripcion: String,
    val tipo: TipoTransaccion,
    val fecha: String = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
)

fun validarTransaccion(montoTexto: String, descripcion: String): String? {
    val monto = montoTexto.toDoubleOrNull()
    return when {
        monto == null || monto <= 0 -> "Ingresa un monto válido mayor a 0"
        descripcion.isBlank() -> "Ingresa una descripción"
        else -> null
    }
}

fun aplicarTransaccion(saldoActual: Double, monto: Double, tipo: TipoTransaccion): Double {
    return if (tipo == TipoTransaccion.INGRESO) saldoActual + monto else saldoActual - monto
}

