package dec18

import java.io.File

fun main() {
    val linesOfMathExercise = getMathExercise()
    val sumOfLines = linesOfMathExercise.map { calculateLine(it, ::executeInsideBrackets) }.sum()
    println("The sum of all the lines is : $sumOfLines")
}

fun calculateLine(
    expression: List<String>,
    calculationToExecuteInsideBrackets: (expressionParts: List<String>) -> Long
): Long {
    val listOfInputParts = expression.toMutableList()
    var lastOpenBracketIndex = listOfInputParts.indexOfLast { it == "(" }

    while (lastOpenBracketIndex != -1) {
        val matchingClosedBracketIndex = lastOpenBracketIndex + listOfInputParts.drop(lastOpenBracketIndex).indexOfFirst { it == ")" }
        if (matchingClosedBracketIndex == -1) throw Error("No closing bracket found!")

        val calculationInput = listOfInputParts.subList(lastOpenBracketIndex + 1, matchingClosedBracketIndex)
        val calculatedValue = calculationToExecuteInsideBrackets(calculationInput)

        listOfInputParts[lastOpenBracketIndex] = calculatedValue.toString()
        for (i in matchingClosedBracketIndex downTo lastOpenBracketIndex + 1) listOfInputParts.removeAt(i)

        lastOpenBracketIndex = listOfInputParts.subList(0, lastOpenBracketIndex).indexOfLast { it == "(" }
    }

    return calculationToExecuteInsideBrackets(listOfInputParts)
}

private fun executeInsideBrackets(listOfOperandsAndNumbers: List<String>): Long {
    var endValue = listOfOperandsAndNumbers[0].toLong()
    var i = 1

    while (i < listOfOperandsAndNumbers.size) {
        endValue = when (val charValue = listOfOperandsAndNumbers[i]) {
            "*" -> Math.multiplyExact(endValue, listOfOperandsAndNumbers[i + 1].toLong())
            "+" -> Math.addExact(endValue, listOfOperandsAndNumbers[i + 1].toLong())
            else -> throw Error("Unknown character : $charValue")
        }
        i += 2
    }

    return endValue
}

fun getMathExercise(): List<List<String>> {
    return File("C:/Work/AdventOfCode/src/dec18/Dec18Input.txt").readLines()
        .map { buildExerciseLine(it) }
}

private fun buildExerciseLine(string: String): List<String> {
    return string.toCharArray().filter { it != ' ' }.map { it.toString() }
}
