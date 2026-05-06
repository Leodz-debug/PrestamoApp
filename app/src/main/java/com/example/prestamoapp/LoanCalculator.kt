package com.example.prestamoapp

import kotlin.math.pow

fun calcularPrestamo(
    monto: Double,
    tasaAnual: Double,
    meses: Int
): Triple<Double, Double, Double> {

    val tasaMensual = tasaAnual / 12 / 100

    val cuota = if (tasaMensual == 0.0) {
        monto / meses
    } else {
        monto * (tasaMensual * (1 + tasaMensual).pow(meses)) /
                ((1 + tasaMensual).pow(meses) - 1)
    }

    val totalPagado = cuota * meses
    val interesTotal = totalPagado - monto

    return Triple(cuota, totalPagado, interesTotal)
}