package com.example.calculatorcompose.model

enum class Symbols(val operator: String) {
    DIVIDE("\u00F7"),
    PLUS_MINUS("\u207A\u2215\u208B"),
    ERASE("\u232B"),
    MINUS("\u2013"),
    PLUS("+"),
    MULTIPLY("x"),
    EQUALS("="),
    DECIMAL("."),
    PERCENT("%")
}

object OperatorSymbols {
    val symbols = Symbols.values().map { it.operator }.toSet()
}