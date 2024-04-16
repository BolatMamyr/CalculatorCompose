package com.example.calculatorcompose.model

sealed class CalcObject {
    data class Number(
        var number: String,
        var isNegative: Boolean = false,
        var isDecimal: Boolean = false
    ) : CalcObject()
    data class Operation(val operation: CalcOperations) : CalcObject()
    object Decimal : CalcObject()
    object Clear : CalcObject()
    object Delete : CalcObject()
    object Calculate : CalcObject()
    object ChangeSign : CalcObject()
    object Percentage : CalcObject()
}

data class CalculationObject(
    var number1: String? = null,
    var number2: String? = null,
    var operation: CalcOperations = CalcOperations.NONE,
    var result: String? = null
)
enum class CalcOperations(val symbol: String) {
    Add(Symbols.PLUS.operator),
    Substract(Symbols.MINUS.operator),
    Multiply(Symbols.MULTIPLY.operator),
    Divide(Symbols.DIVIDE.operator),
    Percentage(Symbols.PERCENT.operator),
    Equals(Symbols.EQUALS.operator),
    NONE("");

    companion object {
        fun getFromOperator(operator: String): CalcOperations {
            return values().firstOrNull { it.symbol == operator } ?: NONE
        }
    }
}