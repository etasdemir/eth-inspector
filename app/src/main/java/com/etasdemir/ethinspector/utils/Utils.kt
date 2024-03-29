package com.etasdemir.ethinspector.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.theme.Negative
import com.etasdemir.ethinspector.ui.theme.Positive
import timber.log.Timber
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.*
import java.util.Locale
import kotlin.math.abs

fun Double.format(digits: Int): Double {
    var smallestAmount = "0."
    for (i in 0 until digits - 1) {
        smallestAmount += "0"
    }
    smallestAmount += "1"
    if (this.toString() < smallestAmount) return smallestAmount.toDouble()


    val pattern = StringBuilder("#.")
    for (i in 0 until digits) {
        pattern.append("#")
    }
    val df = DecimalFormat(pattern.toString(), DecimalFormatSymbols(Locale.ENGLISH))
    return df.format(this).toDouble()
}

fun String.format(digits: Int): String {
    if (digits == 0) {
        return this.split('.')[0]
    }
    var smallestAmount = "0."
    for (i in 0 until digits - 1) {
        smallestAmount += "0"
    }
    smallestAmount += "1"
    if (this < smallestAmount) return smallestAmount

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

@Composable
fun ColoredAmountText(modifier: Modifier = Modifier, amount: BigDecimal, digits: Int = 5) {
    val formattedNumber = remember { amount.setScale(digits, RoundingMode.UP) }
    val amountTextColor: Color?
    val amountText: String?
    val res = amount.compareTo(BigDecimal.ZERO)
    if (res == 0 || res == 1) {
        amountText = "+${formattedNumber.toPlainString()}"
        amountTextColor = Positive
    } else {
        amountText = "-${formattedNumber.toPlainString()}"
        amountTextColor = Negative
    }

    Text(
        modifier = modifier,
        text = stringResource(
            id = R.string.eth_with_amount, amountText
        ), color = amountTextColor, fontSize = 16.sp
    )
}

fun amountColor(amount: Double) = if (amount >= 0) Positive else Negative

fun ULong.toHex() = "0x${this.toString(16)}"

fun String.toDecimal(): ULong? {
    return try {
        this.substring(2).toULong(16)
    } catch (numberFormatEx: NumberFormatException) {
        Timber.e(numberFormatEx.message)
        null
    }
}

fun String.addDots(): String {
    val len = this.length
    if (len < 3) return this

    var current = 0
    val part = StringBuilder()
    val result = StringBuilder()
    part.insert(0, "")
    for (i in len - 1 downTo 0) {
        current++
        part.insert(0, this[i])
        if (current % 3 == 0) {
            result.insert(0, part)
            if (i != 0) {
                result.insert(0, ".")
            }
            part.clear()
            current = 0
        }
    }
    if (len % 3 != 0) {
        result.insert(0, this.substring(0, len % 3))
    }
    return result.toString()
}

private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
fun getDateString(time: Long): String = simpleDateFormat.format(time * 1000L)

fun Double.toPlainString(): String {
    val df = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    df.maximumFractionDigits = 340
    return df.format(this)
}

fun convertTokenAmountFromDecimal(amount: String, tokenDecimal: Int): Double {
    val numbers = amount.toMutableList()
    val diff = numbers.size - tokenDecimal
    if (diff <= 0) {
        for (i in 0..abs(diff)) {
            numbers.add(0, '0')
        }
    }
    numbers.add(numbers.size - tokenDecimal, '.')
    return String(numbers.toCharArray()).format(3).toDouble()
}

fun Context.setAppLocale(language: String, recreateActivity: Boolean) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    resources.updateConfiguration(config, resources.displayMetrics)
    if (recreateActivity) {
        this.findActivity().recreate()
    }
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}
