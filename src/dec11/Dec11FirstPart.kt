package dec11

import java.io.File

const val OCCUPIED_SEAT = '#'
const val VACANT_SEAT = 'L'
const val FLOOR = '.'

fun main() {
    val startSeatMap = getSeatMap()
    val sum = getSumOfOccupiedSeatsAtSteadyState(startSeatMap, ::getNewSeatMap)
    println("Sum: $sum")
}

fun getSumOfOccupiedSeatsAtSteadyState(
    initialSeatMapState: List<List<Char>>,
    getNextSeatMapState: (currentSeatMap: List<List<Char>>) -> List<List<Char>>
): Int {
    var currentSeatMapState = initialSeatMapState
    var seatMapHasReachedSteadyState = false

    while (!seatMapHasReachedSteadyState) {
        val newSeatMapState = getNextSeatMapState(currentSeatMapState)
        seatMapHasReachedSteadyState = currentSeatMapState == newSeatMapState
        currentSeatMapState = newSeatMapState
    }

    return currentSeatMapState.map { seatsInRow -> seatsInRow.countOccupiedSeats() }.sum()
}

private fun getNewSeatMap(seatMap: List<List<Char>>): List<List<Char>> {
    return seatMap.toMutableList().mapIndexed { y, seatsInRow ->
        seatsInRow.mapIndexed { x, seat ->
            when (seat) {
                FLOOR -> FLOOR
                VACANT_SEAT -> if (countAdjacentSeatsThatAreOccupied(seatMap, x, y) == 0) OCCUPIED_SEAT else VACANT_SEAT
                OCCUPIED_SEAT -> if (countAdjacentSeatsThatAreOccupied(seatMap, x, y) >= 4) VACANT_SEAT else OCCUPIED_SEAT
                else -> throw Error("Unexpected character!")
            }
        }
    }
}

private fun countAdjacentSeatsThatAreOccupied(seatMap: List<List<Char>>, x: Int, y: Int): Int {
    val startX = maxOf(x - 1, 0)
    val endX = minOf(x + 1, seatMap[0].size - 1)

    val startY = maxOf(y - 1, 0)
    val endY = minOf(y + 1, seatMap.size - 1)

    val numberOfOccupiedSeatsInBlock = seatMap.subList(startY, endY + 1)
        .map { seatsInRow -> seatsInRow.subList(startX, endX + 1).countOccupiedSeats() }
        .sum()

    val extraCountFromSeat = if (seatMap[y][x].isOccupiedSeat()) 1 else 0

    return numberOfOccupiedSeatsInBlock - extraCountFromSeat
}

fun getSeatMap(): List<List<Char>> {
    return File("C:/Work/AdventOfCode/src/dec11/Dec11Input.txt")
        .readLines()
        .map { it.toList() }
}

fun Char.isVacantSeat(): Boolean {
    return this == VACANT_SEAT
}

fun Char.isOccupiedSeat(): Boolean {
    return this == OCCUPIED_SEAT
}

fun List<Char>.countOccupiedSeats(): Int {
    return this.count { seat -> seat.isOccupiedSeat() }
}
