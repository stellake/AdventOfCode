package dec1

import java.io.File

fun main() {
    val numbers = getNumbers()

    for (i in 0 until numbers.size - 1) {
        for (j in i + 1 until numbers.size) {
            calculateSumAndPrintIfCorrect(numbers[i], numbers[j])
        }
    }
}

private fun calculateSumAndPrintIfCorrect(firstNum: Int, secondNum: Int) {
    if (firstNum + secondNum == 2020) {
        val product = firstNum * secondNum
        println("Found a match! The product is $product. The numbers are $firstNum, $secondNum")
    }
}

fun getNumbers(): List<Int> {
    return File("C:/Work/AdventOfCode/src/dec1/Dec1Input.txt")
        .readLines()
        .map { it.toInt() }
}
