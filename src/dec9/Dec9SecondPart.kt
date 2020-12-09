package dec9

import java.lang.Error

fun main() {
    val numbers = getProgramInput()
    val brokenNumber = findFirstNumberThatDoesntMatchSpec(numbers)

    if (brokenNumber == -1L) throw Error("Incorrect number that doesn't match spec!")

    // Maybe try something more efficient? Sliding window?
    numbers.forEachIndexed { index, number ->
        var sum = number
        var nextNumberIndex = index + 1

        while (sum < brokenNumber) {
            sum += numbers[nextNumberIndex]
            if (sum == brokenNumber) {
                val sumOfMinMaxInRange = findSumOfMinAndMaxValue(numbers.subList(index, nextNumberIndex + 1))
                println("Sum of min max in range: $sumOfMinMaxInRange")
            }
            nextNumberIndex += 1
        }
    }
}

private fun findSumOfMinAndMaxValue(numbers: List<Long>): Long {
    val min = numbers.min() ?: return -1L
    val max = numbers.max() ?: return -1L

    return min + max
}
