package com.etasdemir.ethinspector.utils

import java.text.DecimalFormat

fun Double.format(digits: Int): Double {
    val pattern = StringBuilder("#.")
    for (i in 0 until digits) {
        pattern.append("#")
    }
    val df = DecimalFormat(pattern.toString())
    return df.format(this).toDouble()
}

fun String.clip(digits: Int): String {
    return if (digits >= (this.length / 2)) {
        this
    } else {
        this.take(digits) + "..." + this.takeLast(digits)
    }
}