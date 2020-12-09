package dec9

import java.io.File

const val PREAMBLE_LENGTH = 25

fun main() {
    val numbers = getProgramInput()
    findFirstNumberThatDoesntMatchSpec(numbers)
}

fun findFirstNumberThatDoesntMatchSpec(numbers: List<Long>): Long {
    for (index in PREAMBLE_LENGTH until numbers.size) {
        val currentNumber = numbers[index]
        val previousNumbersToSearchOn = numbers.subList(index - PREAMBLE_LENGTH, index)
        val doNumbersSumUpToCurrentNumber = checkIfNumbersSumUpToCurrentNumber(currentNumber, previousNumbersToSearchOn)

        if (!doNumbersSumUpToCurrentNumber) {
            println("No match in previous numbers for index $index with value $currentNumber")
            return currentNumber
        }
    }

    return -1L
}

private fun checkIfNumbersSumUpToCurrentNumber(currentNumber: Long, numbers: List<Long>): Boolean {
    return numbers.any { number1 ->
        numbers.any { number2 ->
            // Numbers should also not be identical as per spec
            number2 != number1 && number1.plus(number2) == currentNumber
        }
    }
}

fun getProgramInput(): List<Long> {
    val numbers = mutableListOf<Long>()
    File("C:/Work/AdventOfCode/src/dec9/Dec9Input.txt").useLines {
            lines -> lines.forEach { line -> numbers.add(line.toLong()) }
    }
    return numbers
}
