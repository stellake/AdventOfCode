package dec17

import java.io.File

const val ACTIVE_POINT = '#'

data class Coordinate(val x: Int, val y: Int, val z: Int, val w: Int = 0)
data class CoordinateRange(var min: Int, var max: Int) {
    fun increaseRange() {
        min -= 1
        max += 1
    }
}

fun main() {
    val pocketDimension = getPocketDimension()

    val xRange = CoordinateRange(0, pocketDimension[0].size - 1)
    val yRange = CoordinateRange(0, pocketDimension.size - 1)
    val zRange = CoordinateRange(0, 0)

    var activeCoordinates = getActiveCoordinateSet(pocketDimension, xRange, yRange)

    repeat (6) {
        xRange.increaseRange()
        yRange.increaseRange()
        zRange.increaseRange()
        activeCoordinates = getNewStateOfActiveCoordinates(xRange, yRange, zRange, activeCoordinates)
    }

    println("Final number of active coordinates: ${activeCoordinates.size}")
}

private fun getNewStateOfActiveCoordinates(
    xRange: CoordinateRange,
    yRange: CoordinateRange,
    zRange: CoordinateRange,
    currentActiveCoordinates: HashSet<Coordinate>
): HashSet<Coordinate> {
    val newActiveCoordinates = hashSetOf<Coordinate>()
    useCoordinatesInRanges(xRange, yRange, zRange) {
        x, y, z, _ ->
            val nearbyActivePointCount = countOfNearbyActivePoints(currentActiveCoordinates, x, y, z)
            if (currentActiveCoordinates.contains(Coordinate(x, y, z))) {
                if (nearbyActivePointCount in 2..3) newActiveCoordinates.add(Coordinate(x, y, z))
            } else {
                if (nearbyActivePointCount == 3) newActiveCoordinates.add(Coordinate(x, y, z))
            }
    }
    return newActiveCoordinates
}

fun getActiveCoordinateSet(
    pocketDimension: List<List<Char>>,
    xRange: CoordinateRange,
    yRange: CoordinateRange
): HashSet<Coordinate> {
    val activeCoordinates = hashSetOf<Coordinate>()
    useCoordinatesInRanges(xRange, yRange) {
        x, y, z, w -> if (pocketDimension[y][x] == ACTIVE_POINT) activeCoordinates.add(Coordinate(x, y, z, w))
    }
    return activeCoordinates
}

private fun countOfNearbyActivePoints(
    activeCoordinates: HashSet<Coordinate>,
    x: Int,
    y: Int,
    z: Int
): Int {
    var countOfActivePoints = 0
    useCoordinatesInRanges(
        CoordinateRange(x - 1, x + 1),
        CoordinateRange(y - 1, y + 1),
        CoordinateRange(z - 1, z + 1)
    ) {
            x1, y1, z1, _ -> if (activeCoordinates.contains(Coordinate(x1, y1, z1))) countOfActivePoints += 1
    }

    if (activeCoordinates.contains(Coordinate(x, y, z))) countOfActivePoints -= 1

    return countOfActivePoints
}

fun useCoordinatesInRanges(
    xRange: CoordinateRange,
    yRange: CoordinateRange,
    zRange: CoordinateRange = CoordinateRange(0, 0),
    wRange: CoordinateRange = CoordinateRange(0, 0),
    useCoordinates: (x: Int, y: Int, z: Int, w: Int) -> Unit
) {
    for (x in xRange.min..xRange.max) {
        for (y in yRange.min..yRange.max) {
            for (z in zRange.min..zRange.max) {
                for (w in wRange.min..wRange.max) {
                    useCoordinates(x, y, z, w)
                }
            }
        }
    }
}

fun getPocketDimension(): List<List<Char>> {
    return File("C:/Work/AdventOfCode/src/dec17/Dec17Input.txt")
        .readLines()
        .map { it.toList() }
}
