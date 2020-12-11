package dec10

import java.io.File

fun main() {
    val sortedJoltages = buildSortedJoltageList()

    var oneJoltageDiffCount = 0
    var threeJoltageDiffCount = 0

    for (index in 1 until sortedJoltages.size) {
        val diffBetweenThisAndLastJoltage = sortedJoltages[index] - sortedJoltages[index - 1]
        if (diffBetweenThisAndLastJoltage == 1) {
            oneJoltageDiffCount += 1
        } else if (diffBetweenThisAndLastJoltage == 3) {
            threeJoltageDiffCount += 1
        } else if (diffBetweenThisAndLastJoltage > 3 || diffBetweenThisAndLastJoltage < 1) {
            throw Error("This is unexpected! Wrong handling or problems with input data!")
        }
    }

    println("Answer: ${Math.multiplyExact(oneJoltageDiffCount, threeJoltageDiffCount)}")
}

fun buildSortedJoltageList(): List<Int> {
    val joltages = getJoltageList()
    joltages.add(0) // As per spec

    joltages.sort()

    val biggestJoltage = joltages.last()
    joltages.add(biggestJoltage + 3)

    return joltages
}

fun getJoltageList(): MutableList<Int> {
    val numbers = mutableListOf<Int>()
    File("C:/Work/AdventOfCode/src/dec10/Dec10Input.txt").useLines {
            lines -> lines.forEach { line -> numbers.add(line.toInt()) }
    }
    return numbers
}
