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
        val format = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(getDefault())
        format.currency = currency
        format.maximumFractionDigits = maximumFractionDigits
        format.minimumFractionDigits = minimumFractionDigits

        if (!withCurrencySymbol) {
            format.currency = null
        }
        return format.format(amount)
    }
}

actual fun CurrencyFormatter(): CurrencyFormatter = AndroidCurrencyFormatter()