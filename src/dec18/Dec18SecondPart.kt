package dec18

import getProduct

fun main() {
    val input = getMathExercise()
    val sum = input.map { calculateLine(it, ::executeInsideBrackets) }.sum()
    println("Sum in part 2: $sum")
}

private fun executeInsideBrackets(listOfOperatorsAndNumbers: List<String>): Long {
    val list = listOfOperatorsAndNumbers.toMutableList()
    var indexOfFirstAddition = list.indexOfFirst { it == "+" }

    while (indexOfFirstAddition != -1) {
        val sum = Math.addExact(list[indexOfFirstAddition - 1].toLong(), list[indexOfFirstAddition + 1].toLong())

        list[indexOfFirstAddition - 1] = sum.toString()
        for (i in indexOfFirstAddition + 1 downTo indexOfFirstAddition) list.removeAt(i)

        indexOfFirstAddition = list.indexOfFirst { it == "+" }
    }

    return list.filterIndexed { index, _ -> index % 2 == 0 }
        .map { it.toLong() }
        .getProduct()
}
