/*
package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

//https://kotlinlang.org/docs/object-declarations.html#creating-anonymous-objects-from-scratch.

class CalculatorLogic(mainScreen: String) {

        fun currentInput(): String = mainScreen.text.toString()

        fun firstZeroCheckFun() { //TODO crashes app
            //if (!currentInput().contains("0.")) {
            //    for (i in 0..1) {
            //        if (currentInput()[i].toString() == "0") {
            //            calculatorScreen.setText(currentInput().drop(i))
            //        }
            //
            //    }
            //
            //    //if (currentInput()[0].toString() == "0" ) {
            //    //    val newScreen = calculatorScreen.text.drop(1)
            //    //    calculatorScreen.setText(newScreen)
            //    //    //Toast.makeText(this, newScreen, Toast.LENGTH_SHORT).show()
            //    //}
            //}
        }

        fun oneSymbolCheck(replacement: String) {
            val oneSymbol = mainScreen.text.toString()
            val targetedSymbol = when {
                oneSymbol.contains("+") -> oneSymbol.replace("+", replacement)
                oneSymbol.contains("-") -> oneSymbol.replace("-", replacement)
                oneSymbol.contains("*") -> oneSymbol.replace("*", replacement)
                oneSymbol.contains("/") -> oneSymbol.replace("/", replacement)
                else -> oneSymbol
            }
            mainScreen.text = targetedSymbol
        }

        fun singleSymbolCheck(screenState: String): Boolean =
            !screenState.split("").any { it == "+" || it == "-" || it == "*" || it == "/" }

        //fun numberStorage() {  }

        fun equalMathOperation(symbol: String) {
            val operation: Float = when {
                symbol.contains("+") -> symbol.split("+")[0].toFloat() + symbol.split("+")[1].toFloat()
                symbol.contains("-") -> symbol.split("-")[0].toFloat() - symbol.split("-")[1].toFloat()
                symbol.contains("*") -> symbol.split("*")[0].toFloat() * symbol.split("*")[1].toFloat()
                symbol.contains("/") -> symbol.split("/")[0].toFloat() / symbol.split("/")[1].toFloat()
                else -> 0.0f
            }
            mainScreen.text = operation.toString()
        }

        fun problemConstructor(firstNumber: String, symbol: String, secondNumber: String): String {
            val number1 = firstNumber.toFloat()
            val number2 = secondNumber.toFloat()
            val result = when (symbol) {
                "+" -> number1 + number2
                "-" -> number1 - number2
                "×" -> number1 * number2
                "÷" -> number1 / number2
                else -> error("problemConstructor FUNCTION ERROR")
            }
            return result.toString()
        }

        fun minusRemoval() {
            if (mainScreen.text.toString().contains("-")) {
                mainScreen.text.toString().replace("-", "")
            }
        }

        fun clear() {
            mainScreen.text = (""); secondaryScreen.text = ""
        }




        val numberStorage = NumberStorage()

        button0.setOnClickListener {
            mainScreen.append("0")
            if (currentInput().length > 1) {
                firstZeroCheckFun()
            }
        }

        button1.setOnClickListener {
            mainScreen.append("1")
            firstZeroCheckFun()
        }

        button2.setOnClickListener {
            mainScreen.append("2")
            firstZeroCheckFun()
        }

        button3.setOnClickListener {
            mainScreen.append("3")
            firstZeroCheckFun()
        }

        button4.setOnClickListener {
            mainScreen.append("4")
            firstZeroCheckFun()
        }

        button5.setOnClickListener {
            mainScreen.append("5")
            firstZeroCheckFun()
        }

        button6.setOnClickListener {
            mainScreen.append("6")
            firstZeroCheckFun()
        }

        button7.setOnClickListener {
            mainScreen.append("7")
            firstZeroCheckFun()
        }

        button8.setOnClickListener {
            mainScreen.append("8")
            firstZeroCheckFun()
        }

        button9.setOnClickListener {
            mainScreen.append("9")
            firstZeroCheckFun()
        }

        dotButton.setOnClickListener {
            if (!currentInput().isIntegerCheck()) {
                mainScreen.append("0.")
            } else if (!currentInput().contains('.')) {
                mainScreen.append(".")
            }
        }

        equalButton.setOnClickListener {
            numberStorage.secondNumber = mainScreen.text.toString()
            val result = problemConstructor(
                numberStorage.firstNumber,
                numberStorage.symbol,
                numberStorage.secondNumber
            )
            mainScreen.text = result
            secondaryScreen.text = numberStorage.firstNumber + numberStorage.symbol + numberStorage.secondNumber
            //equalMathOperation(calculatorScreen.text.toString())
        }

        addButton.setOnClickListener {
            numberStorage.firstNumber = mainScreen.text.toString()
            //numberStorage.negativeSymbol = ""
            numberStorage.symbol = "+"
            clear()

            //numberStorage.number = calculatorScreen.text.toString()
            //if (singleSymbolCheck(calculatorScreen.text.toString())) {
            //calculatorScreen.append("+") }
            //oneSymbolCheck("+")
            //calculatorScreen.setText("")
            //}
        }

        subtractButton.setOnClickListener {
            if (currentInput().isEmpty()) { //THIS IS CORRECT
                mainScreen.append("-")
                Toast.makeText(this, "in 1. IF", Toast.LENGTH_SHORT).show()
            }

            if (currentInput().isIntegerCheck()) {
                Toast.makeText(this, currentInput() + "behind 2. IF", Toast.LENGTH_SHORT).show()
                numberStorage.firstNumber = mainScreen.text.toString()
                numberStorage.symbol = "-"
                clear()
            }
            //if (calculatorScreen.text.toString().contains("-")) {
            //    calculatorScreen.append("-")
            //}
            //if (singleSymbolCheck(calculatorScreen.text.toString())) { calculatorScreen.append("-") }
            //oneSymbolCheck("-")
        }

        multiplyButton.setOnClickListener {
            numberStorage.firstNumber = mainScreen.text.toString()
            numberStorage.symbol = "×"
            //numberStorage.negativeSymbol = ""
            clear()

            //if (singleSymbolCheck(calculatorScreen.text.toString())) { calculatorScreen.append("*") }
            //oneSymbolCheck("*")
        }

        divideButton.setOnClickListener {
            numberStorage.firstNumber = mainScreen.text.toString()
            numberStorage.symbol = "÷"
            //numberStorage.negativeSymbol = ""
            clear()

            //if (singleSymbolCheck(calculatorScreen.text.toString())) { calculatorScreen.append("/") }
            //oneSymbolCheck("/")
        }

        clearButton.setOnClickListener { clear() }
    }
}

 */