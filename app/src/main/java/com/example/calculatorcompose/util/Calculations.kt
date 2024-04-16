package com.example.calculatorcompose.util

import com.example.calculatorcompose.model.CalcOperations

fun calculate(topNumber: String, bottomNumber: String, operator: CalcOperations): String {
    val res = when (operator) {
        CalcOperations.Add -> {
            (topNumber.toDouble() + bottomNumber.toDouble())
        }
        CalcOperations.Substract -> (topNumber.toDouble() - bottomNumber.toDouble())
        CalcOperations.Multiply -> (topNumber.toDouble() * bottomNumber.toDouble())
        CalcOperations.Divide -> (topNumber.toDouble() / bottomNumber.toDouble())
        CalcOperations.Percentage -> (topNumber.toDouble() * bottomNumber.toDouble() / 100)
        else -> .0
    }

    return if (res.isDecimal()) {
        res.toString()
    } else {
        res.toLong().toString()
    }
}