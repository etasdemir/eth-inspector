package com.etasdemir.ethinspector.utils

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

@Composable
fun ColoredAmountText(modifier: Modifier = Modifier, amount: Double, digits: Int = 5) {
    val formattedNumber = remember { amount.format(digits) }
    val amountTextColor: Color?
    val amountText: String?
    if (amount >= 0) {
        amountText = "+$formattedNumber"
        amountTextColor = Positive
    } else {
        amountText = "-$formattedNumber"
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
