package com.example.calculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var theResult = NumberStorage().theResult
    private var onScreenEquation = NumberStorage().equation


    //TODO needs work
    private fun resultCommaSeparation(equation: String): String {
        //var replacePart: String = equation
        //val maxIndex = equation.length - 3
        //for (i in maxIndex downTo 1 step 3) {
        //    replacePart = replacePart.replaceRange(i, i, ",")
        //}
        //return replacePart
        return equation
    }

    private fun numberOfSymbols(equation: String): Int = equation.filter { it == '+' || it == '-' || it == '×' || it == '÷'}.count()

    private fun multipleDotsCheck(equation: String): Boolean {
        return if (equation.contains('.'))
            equation.filter { it == '.' }.count() < numberOfSymbols(equation) + 1
        else true
    }

    private fun multipleSymbolsCheck(equation: String): Boolean {
        val targetedSymbolIndex: Int = if (equation.length > 1) equation.lastIndex else 0
        return if (equation.isEmpty()) {
            true    // TRUE IS FOR MINUS SYMBOL
        } else {
            when (equation[targetedSymbolIndex]) {
                '+' -> false
                '-' -> false
                '×' -> false
                '÷' -> false
                else -> true
            }
        }
    }

    private fun emptyScreen(eq: String): Boolean = eq != ""

    private fun removeLastZeroOrDot(equation: String): String {
        val lastCharacter: Int = equation.length - 1
        return if (equation[lastCharacter - 1] == '.' && equation[lastCharacter - 1] == '.') {
            equation.replace(".0", "")
        } else equation
    }

    private fun equationScreenReset() {
        if (secondaryScreen.text == onScreenEquation) {
            theResult = ""
            onScreenEquation = ""
            mainScreen.text = ""
            secondaryScreen.text = ""
        }
    }

    private fun oneCharRemove() {
        mainScreen.text = mainScreen.text.toString().dropLast(1)
        secondaryScreen.text = secondaryScreen.text.toString().dropLast(1)
        onScreenEquation = onScreenEquation.dropLast(1)
    }

    private fun clear() {
        theResult = ""
        onScreenEquation = ""
        mainScreen.text = ("")
        secondaryScreen.text = ""
    }

    // this will create list of symbols in a correct order separated from numbers
    private fun separatedSymbols(equation: String): MutableList<Char> {
        var equationV = equation
        if (equationV[0] == '-') equationV = equationV.drop(1)
        return equationV.filter { !it.isDigit() && it != '.' }.toMutableList()
    }

    // this will create a list of numbers in a correct order from input, separated by symbols
    private fun getOnlyNumbers(equation: String): MutableList<Double> {
        val equationMuList = equation.toMutableList()
        val numberMuList = mutableListOf<Double>()
        var numberString = ""
        var minusLocation = ""

        if (equationMuList[0] == '-') {
            equationMuList.removeAt(0); minusLocation += "-"
        }
        for (j in 0 until equationMuList.size) {
            if (equationMuList[j].isDigit() || equationMuList[j] == '.') {
                if (minusLocation == "-") {
                    numberString += "-${equationMuList[j]}"; minusLocation = ""
                } else numberString += equationMuList[j]
            } else {
                numberMuList.add(numberString.toDouble())
                numberString = ""
            }
        }
        numberMuList.add(numberString.toDouble())
        return numberMuList
    }

    // this function will choose index of the symbol that has to be used in a calculation as a first one.
    // Then this index will be used to choose correct symbol and numbers around it that has to be calculated.
    private fun strongestSymbol(symbolsString: String): Int {
        val tString = symbolsString.toCharArray()
        val plu = tString.indexOf('+')
        val min = tString.indexOf('-')
        val mul = tString.indexOf('×')
        val del = tString.indexOf('÷')
        return (
                if (symbolsString.contains("×") && symbolsString.contains("/")) {
                    if (tString.indexOf('×') < tString.indexOf('÷')) mul
                    else del
                } else if (symbolsString.contains("×")) mul
                else if (symbolsString.contains("÷")) del
                else if (symbolsString.contains("+") && symbolsString.contains("-")) {
                    if (tString.indexOf('+') < tString.indexOf('-')) plu
                    else min
                } else if (symbolsString.contains("+")) plu
                else if (symbolsString.contains("-")) min
                else -1
                )
    }

    // function of boolean to choose if equation has second partner for calculation
    private fun equationHasPartner(problem: String): Boolean {
        val equationMuList = problem.toMutableList()
        val numberMuList = mutableListOf<Double>()
        var numberString = ""
        if (equationMuList[0] == '-') equationMuList.removeAt(0)
        for (j in equationMuList.indices) {
            if (equationMuList[j].isDigit()) {
                numberString += equationMuList[j]
            } else {
                numberMuList.add(numberString.toDouble())
                numberString = ""
            }
        }
        numberMuList.add(numberString.toDouble())
        return numberMuList.size >= 2
    }

    // joins all functions above this and calculates equation from the input String
    private fun theFinalResult(problem: String): String {
        var calculationOfPairs = 0.0
        if (equationHasPartner(problem)) {
            val allSymbols: MutableList<Char> = separatedSymbols(problem)
            val numbersList: MutableList<Double> = getOnlyNumbers(problem)

            for (i in allSymbols.indices) {
                val strongestSymbolIndex: Int = strongestSymbol(allSymbols.joinToString(""))
                calculationOfPairs = when (allSymbols[strongestSymbolIndex]) {
                    '×' -> numbersList[strongestSymbolIndex] * numbersList[strongestSymbolIndex + 1]
                    '÷' -> numbersList[strongestSymbolIndex] / numbersList[strongestSymbolIndex + 1]
                    '-' -> numbersList[strongestSymbolIndex] - numbersList[strongestSymbolIndex + 1]
                    '+' -> numbersList[strongestSymbolIndex] + numbersList[strongestSymbolIndex + 1]
                    else -> 0.0
                }
                if (numbersList.size != 2) repeat(2) { numbersList.removeAt(strongestSymbolIndex) }
                allSymbols.removeAt(strongestSymbolIndex)
                numbersList.add(strongestSymbolIndex, calculationOfPairs)
            }
        }
        //Toast.makeText(this, calculationOfPairs.toString(), Toast.LENGTH_SHORT).show()
        return if (calculationOfPairs != 0.0) {
            theResult = calculationOfPairs.toString()
            removeLastZeroOrDot(theResult)
        } else ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button0.setOnClickListener {
            if (emptyScreen(mainScreen.text.toString())) {
                equationScreenReset()
                onScreenEquation += "0"
                mainScreen.text = onScreenEquation
                secondaryScreen.text = theFinalResult(onScreenEquation)
            }
        }
        button1.setOnClickListener {
            equationScreenReset()
            onScreenEquation += "1"
            mainScreen.text = onScreenEquation
            secondaryScreen.text = theFinalResult(onScreenEquation)
        }
        button2.setOnClickListener {
            equationScreenReset()
            onScreenEquation += "2"
            mainScreen.text = onScreenEquation
            secondaryScreen.text = theFinalResult(onScreenEquation)
        }
        button3.setOnClickListener {
            equationScreenReset()
            onScreenEquation += "3"
            mainScreen.text = onScreenEquation
            secondaryScreen.text = theFinalResult(onScreenEquation)
        }
        button4.setOnClickListener {
            equationScreenReset()
            onScreenEquation += "4"
            mainScreen.text = onScreenEquation
            secondaryScreen.text = theFinalResult(onScreenEquation)
        }
        button5.setOnClickListener {
            equationScreenReset()
            onScreenEquation += "5"
            mainScreen.text = onScreenEquation
            secondaryScreen.text = theFinalResult(onScreenEquation)
        }
        button6.setOnClickListener {
            equationScreenReset()
            onScreenEquation += "6"
            mainScreen.text = onScreenEquation
            secondaryScreen.text = theFinalResult(onScreenEquation)
        }
        button7.setOnClickListener {
            equationScreenReset()
            onScreenEquation += "7"
            mainScreen.text = onScreenEquation
            secondaryScreen.text = theFinalResult(onScreenEquation)
        }
        button8.setOnClickListener {
            equationScreenReset()
            onScreenEquation += "8"
            mainScreen.text = onScreenEquation
            secondaryScreen.text = theFinalResult(onScreenEquation)
        }
        button9.setOnClickListener {
            equationScreenReset()
            onScreenEquation += "9"
            mainScreen.text = onScreenEquation
            secondaryScreen.text = theFinalResult(onScreenEquation)
        }

        addButton.setOnClickListener {
            if (emptyScreen(mainScreen.text.toString())) {
                if (multipleSymbolsCheck(mainScreen.text.toString())) {
                    onScreenEquation += "+"
                    mainScreen.text = onScreenEquation
                }
            }
        }
        subtractButton.setOnClickListener {
            if (multipleSymbolsCheck(mainScreen.text.toString())) {
                onScreenEquation += "-"
                mainScreen.text = onScreenEquation
            }
        }
        multiplyButton.setOnClickListener {
            if (emptyScreen(mainScreen.text.toString())) {
                if (multipleSymbolsCheck(mainScreen.text.toString())) {
                    onScreenEquation += "×"
                    mainScreen.text = onScreenEquation
                }
            }
        }
        divideButton.setOnClickListener {
            if (emptyScreen(mainScreen.text.toString())) {
                if (multipleSymbolsCheck(mainScreen.text.toString())) {
                    onScreenEquation += "÷"
                    mainScreen.text = onScreenEquation
                }
            }
        }

        dotButton.setOnClickListener {
            if (emptyScreen(mainScreen.text.toString())) {
                if (multipleDotsCheck(mainScreen.text.toString())) {
                    onScreenEquation += "."; mainScreen.text = onScreenEquation
                }
            }
        }
        equalButton.setOnClickListener {
            if (theResult.isNotEmpty()) {
                mainScreen.text = resultCommaSeparation(removeLastZeroOrDot(theResult))
                secondaryScreen.text = onScreenEquation//removeLastZeroOrDot(showedEquation)
            }
        }

        clearButton.setOnClickListener { oneCharRemove() }
        clearButton.setOnLongClickListener { clear(); true }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> Toast.makeText(this, "about", Toast.LENGTH_SHORT).show()
            R.id.versions -> Toast.makeText(this, "versions", Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

}


//TODO disable buttons after text in textView has size of 25sp
//TODO create error exceptions so app doesn't always crash
//TODO symbols are clickable after dot
//TODO screen shadow
// 3 TODO every 3 numbers from end separate by comma
//TODO replace last symbol with new symbol the user wants to type

// 1 TODO fill out menu with some content..
    //"about" -> make pop-up with app information
    //"versions" -> make pop- with newest updates, versions...
    //"settings" -> change app color theme