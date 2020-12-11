package dec10

fun main() {
    val joltages = buildSortedJoltageList()

    val mapOfJoltageToHowManyRoutesFromZero = mutableMapOf<Int, Long>()

    mapOfJoltageToHowManyRoutesFromZero[joltages[0]] = 1
    mapOfJoltageToHowManyRoutesFromZero[joltages[1]] = 1 // always connects only to 0

    for (index in 2 until joltages.size) {
        val currentJoltage = joltages[index]

        val joltagesThatConnectToCurrentJoltage = mutableListOf(joltages[index - 1])
        if (currentJoltage - joltages[index - 2] <= 3) joltagesThatConnectToCurrentJoltage.add(joltages[index - 2])
        if (index > 2 && currentJoltage - joltages[index - 3] <= 3) joltagesThatConnectToCurrentJoltage.add(joltages[index - 3])

        val sumOfConnectingJoltageRoutes = mapOfJoltageToHowManyRoutesFromZero.getSumOfJoltageRoutes(joltagesThatConnectToCurrentJoltage)
        mapOfJoltageToHowManyRoutesFromZero[currentJoltage] = sumOfConnectingJoltageRoutes
    }

    println("Number of possible routes: ${mapOfJoltageToHowManyRoutesFromZero[joltages.last()]}")
}

private fun Map<Int, Long>.getSumOfJoltageRoutes(joltages: List<Int>): Long {
    return joltages.map { joltage -> this[joltage] ?: 0L }.sum()
}
