package com.example.calculatorcompose.util

import com.example.calculatorcompose.model.OperatorSymbols

fun String.hasOperator(): Boolean {
    return this.any { it.toString() in OperatorSymbols.symbols }
}

fun Char.isOperator(): Boolean {
    return this.toString() in OperatorSymbols.symbols
}

fun String.replaceLast(newValue: String) = if (isNotEmpty()) dropLast(1) + newValue else this

/**
 * Adds or replaces symbol at the end
 */
fun String.addOrReplaceSymbol(operator: String): String {
    if (isEmpty()) return ""

    return when {
        last().isOperator() -> replaceLast(operator)
        else -> this + operator
    }
}

fun Double.isDecimal(): Boolean = this % 1 != .0
