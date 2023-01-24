package com.etasdemir.ethinspector.utils

import com.etasdemir.ethinspector.ui.theme.Negative
import com.etasdemir.ethinspector.ui.theme.Positive
import java.text.DecimalFormat

fun Double.format(digits: Int): Double {
    val pattern = StringBuilder("#.")
    for (i in 0 until digits) {
        pattern.append("#")
    }
    val df = DecimalFormat(pattern.toString())
    return df.format(this).toDouble()
}

fun String.format(digits: Int): String {
    val delimiter = "."
    val parts = this.split(delimiter)
    val lastPart = parts.last().toCharArray()
    val formattedString = StringBuilder("")
    for (i in 0 until parts.size - 1) {
        formattedString.append(parts[i] + delimiter)
    }
    for (i in lastPart.indices) {
        if (i < digits) {
            formattedString.append(lastPart[i])
        }
    }
    return formattedString.toString()
}

fun String.clip(digits: Int): String {
    return if (digits >= (this.length / 2)) {
        this
    } else {
        this.take(digits) + "..." + this.takeLast(digits)
    }
}

fun amountColor(amount: Double) = if (amount >= 0) Positive else Negative