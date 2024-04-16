package com.example.calculatorcompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculatorcompose.model.CalcOperations
import com.example.calculatorcompose.ui.theme.CalculatorComposeTheme
import com.example.calculatorcompose.ui.theme.*
import com.example.calculatorcompose.model.Symbols
import com.example.calculatorcompose.util.MaxInputLenght
import com.example.calculatorcompose.util.addOrReplaceSymbol
import com.example.calculatorcompose.util.calculate

// TODO: FIX: bottom number, new chars can be appended to result.
// TODO: FIX: bottom number: new 0s can be appended to decimal (1.000 x 2 = 2.000)
// TODO: FIX: goes to 2nd line
// TODO: FIX: wrong colors in S21
// TODO: FIX: multiple error toasts can be shown right away if clicking fast
// TODO: move Composables to separate file
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorComposeTheme {
                CalculatorPage(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

@Preview
@Composable
fun CalculatorPage(modifier: Modifier = Modifier) {
    var bottomNumber by remember { mutableStateOf("") }
    var topNumber by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf(CalcOperations.NONE) }
    var result by remember { mutableStateOf("") }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TopNumber(text = topNumber)
            Spacer(modifier = Modifier.height(10.dp))
            Operator(text = operator.symbol)
            Spacer(modifier = Modifier.height(10.dp))
            BottomNumber(text = bottomNumber)
            Spacer(modifier = Modifier.height(10.dp))
            // Line 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = "C", color = GreySolid) { _, _ ->
                    bottomNumber = ""
                    topNumber = ""
                    operator = CalcOperations.NONE
                    result = ""
                }
                CalculatorButton(text = Symbols.PLUS_MINUS.operator, color = GreySolid, fontSize = 20.sp) { _, _ ->
                    bottomNumber = if (bottomNumber.take(1) == Symbols.MINUS.operator) {
                        bottomNumber.drop(1)
                    } else {
                        Symbols.MINUS.operator + bottomNumber
                    }
                }
                CalculatorButton(text = Symbols.PERCENT.operator, color = GreySolid) { op, _ ->
                    val newOperator = CalcOperations.getFromOperator(op)
                    if (topNumber.isEmpty()) {
                        topNumber = bottomNumber
                    } else if (bottomNumber.isNotEmpty()) {
                        topNumber = calculate(topNumber, bottomNumber, newOperator)
                    }
                    bottomNumber = ""
                    operator = newOperator
                }
                CalculatorButton(text = Symbols.DIVIDE.operator, color = ChinesePurple) { op, _ ->
                    val newOperator = CalcOperations.getFromOperator(op)
                    if (topNumber.isEmpty()) {
                        topNumber = bottomNumber
                    } else if (bottomNumber.isNotEmpty()) {
                        topNumber = calculate(topNumber, bottomNumber, newOperator)
                    }
                    bottomNumber = ""
                    operator = newOperator
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Line 2
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = "7", color = CharcoalSolid) { _, context ->
                    if (bottomNumber.length >= MaxInputLenght) {
                        Toast.makeText(context,
                            context.getString(R.string.more_than_12_digits_error), Toast.LENGTH_SHORT).show()
                    } else {
                        bottomNumber += "7"
                    }
                }
                CalculatorButton(text = "8", color = CharcoalSolid) { _, context ->
                    bottomNumber += "8"
                }
                CalculatorButton(text = "9", color = CharcoalSolid) { _, context ->
                    bottomNumber += "9"
                }
                CalculatorButton(text = Symbols.MULTIPLY.operator, color = ChinesePurple) { op, _ ->
                    val newOperator = CalcOperations.getFromOperator(op)
                    if (topNumber.isEmpty()) {
                        topNumber = bottomNumber
                    } else if (bottomNumber.isNotEmpty()) {
                        topNumber = calculate(topNumber, bottomNumber, newOperator)
                    }
                    bottomNumber = ""
                    operator = newOperator
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Line 3
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = "4", color = CharcoalSolid) { _, context ->
                    bottomNumber += "4"
                }
                CalculatorButton(text = "5", color = CharcoalSolid) { _, context ->
                    bottomNumber += "5"
                }
                CalculatorButton(text = "6", color = CharcoalSolid) { _, context ->
                    bottomNumber += "6"
                }
                CalculatorButton(text = Symbols.MINUS.operator, color = ChinesePurple, fontSize = 25.sp) { op, _ ->
                    val newOperator = CalcOperations.getFromOperator(op)
                    if (topNumber.isEmpty()) {
                        topNumber = bottomNumber
                    } else if (bottomNumber.isNotEmpty()) {
                        topNumber = calculate(topNumber, bottomNumber, newOperator)
                    }
                    bottomNumber = ""
                    operator = newOperator
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Line 4
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = "1", color = CharcoalSolid) { _, context ->
                    bottomNumber += "1"
                }
                CalculatorButton(text = "2", color = CharcoalSolid) { _, context ->
                    bottomNumber += "2"
                }
                CalculatorButton(text = "3", color = CharcoalSolid) { _, context ->
                    bottomNumber += "3"
                }
                CalculatorButton(text = Symbols.PLUS.operator, color = ChinesePurple) { op, _ ->
                    val newOperator = CalcOperations.getFromOperator(op)
                    if (topNumber.isEmpty()) {
                        topNumber = bottomNumber
                    } else if (bottomNumber.isNotEmpty()) {
                        topNumber = calculate(topNumber, bottomNumber, newOperator)
                    }
                    bottomNumber = ""
                    operator = newOperator
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Line 5
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CalculatorButton(text = Symbols.DECIMAL.operator, color = CharcoalSolid) { _, _ ->
                    if (bottomNumber.contains(Symbols.DECIMAL.operator)) return@CalculatorButton
                    bottomNumber = bottomNumber.addOrReplaceSymbol(Symbols.DECIMAL.operator)
                }
                CalculatorButton(text = "0", color = CharcoalSolid) { _, context ->
                    bottomNumber += "0"
                }
                CalculatorButton(text = Symbols.ERASE.operator, color = CharcoalSolid, fontSize = 25.sp) { _, _ ->
                    bottomNumber = bottomNumber.dropLast(1)
                }
                CalculatorButton(text = Symbols.EQUALS.operator, color = ChinesePurple) { op, _ ->
                    if (topNumber.isNotEmpty() && bottomNumber.isNotEmpty() && operator != CalcOperations.NONE) {
                        bottomNumber = calculate(topNumber, bottomNumber, operator)
                        operator = CalcOperations.getFromOperator(op)
                    } else if (topNumber.isNotEmpty()) {
                        // bottom number and/or operator is empty
                        bottomNumber = topNumber
                        operator = CalcOperations.NONE
                    } else {
                        operator = CalcOperations.NONE
                    }
                    topNumber = ""
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun TopNumber(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        text = text,
        maxLines = 1,
        fontWeight = FontWeight.Light,
        fontSize = 30.sp,
        color = TextGray,
        textAlign = TextAlign.End
    )
}

@Composable
fun BottomNumber(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        text = text,
        maxLines = 1,
        fontSize = 40.sp,
        color = Color.White,
        textAlign = TextAlign.End
    )
}

@Composable
fun Operator(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier
            .fillMaxWidth(),
        text = text,
        fontSize = 30.sp,
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

@Composable
fun CalculatorButton(modifier: Modifier = Modifier, text: String, color: Color, fontSize: TextUnit = 30.sp, onClick: (String, Context) -> Unit = {_,_->}) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .size(70.dp)
            .background(color, CircleShape)
            .clickable {
                onClick(text, context)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            color = Color.White
        )
    }
}

