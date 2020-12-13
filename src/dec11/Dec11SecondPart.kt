package dec11

data class Coordinates(
    val x: Int,
    val y: Int
)

fun main() {
    val initialSeatMap = getSeatMap()
    val sum = getSumOfOccupiedSeatsAtSteadyState(initialSeatMap, ::getNewSeatMap)
    println("Sum: $sum")
}

private fun getNewSeatMap(seatMap: List<List<Char>>): List<List<Char>> {
    return seatMap.toMutableList().mapIndexed { y, seatsInRow ->
        seatsInRow.mapIndexed { x, seat ->
            when (seat) {
                FLOOR -> FLOOR
                VACANT_SEAT -> if (countFirstVisibleSeatsThatAreOccupied(seatMap, x, y) == 0) OCCUPIED_SEAT else VACANT_SEAT
                OCCUPIED_SEAT -> if (countFirstVisibleSeatsThatAreOccupied(seatMap, x, y) >= 5) VACANT_SEAT else OCCUPIED_SEAT
                else -> throw Error("Unexpected character!")
            }
        }
    }
}

private fun countFirstVisibleSeatsThatAreOccupied(input: List<List<Char>>, x: Int, y: Int): Int {
    val horizontalCoordinateChanges = listOf(Coordinates(1, 0), Coordinates(-1, 0))
    val numberOfOccupiedSeatsInRow = countFirstVisibleSeatsForCoordinateChanges(input, x, y, horizontalCoordinateChanges)

    val verticalCoordinateChanges = listOf(Coordinates(0, 1), Coordinates(0, -1))
    val numberOfOccupiedSeatsInColumn = countFirstVisibleSeatsForCoordinateChanges(input, x, y, verticalCoordinateChanges)

    val diagonalCoordinateChanges = listOf(Coordinates(1, 1), Coordinates(-1, 1), Coordinates(1, -1), Coordinates(-1, -1))
    val numberOfOccupiedSeatsDiagonally = countFirstVisibleSeatsForCoordinateChanges(input, x, y, diagonalCoordinateChanges)

    return numberOfOccupiedSeatsInRow + numberOfOccupiedSeatsInColumn + numberOfOccupiedSeatsDiagonally
}

private fun countFirstVisibleSeatsForCoordinateChanges(input: List<List<Char>>, x: Int, y: Int, coordinateChanges: List<Coordinates>): Int {
    return coordinateChanges
        .map { coordinateChange -> isNextVisibleSeatOccupied(input, Coordinates(x, y), coordinateChange) }
        .count { isSeatOccupied -> isSeatOccupied }
}

private fun isNextVisibleSeatOccupied(seatMap: List<List<Char>>, startCoordinates: Coordinates, coordinateChange: Coordinates): Boolean {
    var x = startCoordinates.x + coordinateChange.x
    var y = startCoordinates.y + coordinateChange.y

    while (x < seatMap[0].size && y < seatMap.size && x >= 0 && y >= 0) {
        if (seatMap[y][x].isOccupiedSeat()) {
            return true
        } else if (seatMap[y][x].isVacantSeat()) {
            return false
        }

        x += coordinateChange.x
        y += coordinateChange.y
    }

    return false
}
