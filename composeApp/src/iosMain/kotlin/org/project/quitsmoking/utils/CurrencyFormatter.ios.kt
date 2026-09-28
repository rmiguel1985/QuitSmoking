package org.project.quitsmoking.utils

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.currencyCode
import platform.Foundation.currentLocale


internal class IOSCurrencyFormatter : CurrencyFormatter {
    override fun format(
        amount: Double,
        withCurrencySymbol: Boolean,
        minimumFractionDigits: Int,
        maximumFractionDigits: Int,
    ): String {
        val formatter = NSNumberFormatter()
        formatter.locale = NSLocale.currentLocale()
        formatter.numberStyle = if (withCurrencySymbol) {
            NSNumberFormatterCurrencyStyle
        } else {
            NSNumberFormatterDecimalStyle
        }
        if (withCurrencySymbol) {
            formatter.currencyCode = NSLocale.currentLocale().currencyCode() ?: "EUR"
        }
        formatter.maximumFractionDigits = maximumFractionDigits.toULong()
        formatter.minimumFractionDigits = minimumFractionDigits.toULong()

        val decimalNumber = NSNumber(amount)
        val formattedString = formatter.stringFromNumber(decimalNumber) ?: "$amount"
        return formattedString.replace(" ", "")
    }
}

actual fun CurrencyFormatter(): CurrencyFormatter = IOSCurrencyFormatter()