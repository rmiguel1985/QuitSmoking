package org.project.quitsmoking.utils

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale.*

internal class AndroidCurrencyFormatter : CurrencyFormatter {
    override fun format(
        amount: Double,
        withCurrencySymbol: Boolean,
        minimumFractionDigits: Int,
        maximumFractionDigits: Int,
    ): String {
        val format = if (withCurrencySymbol) {
            NumberFormat.getCurrencyInstance().apply {
                currency = Currency.getInstance(getDefault())
            }
        } else {
            NumberFormat.getNumberInstance()
        }
        format.maximumFractionDigits = maximumFractionDigits
        format.minimumFractionDigits = minimumFractionDigits
        return format.format(amount)
    }
}

actual fun CurrencyFormatter(): CurrencyFormatter = AndroidCurrencyFormatter()