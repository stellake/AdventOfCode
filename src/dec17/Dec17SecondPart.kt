package dec17

fun main() {
    val pocketDimension = getPocketDimension()

    val xRange = CoordinateRange(0, pocketDimension[0].size - 1)
    val yRange = CoordinateRange(0, pocketDimension.size - 1)
    val zRange = CoordinateRange(0, 0)
    val wRange = CoordinateRange(0, 0)

    var activeCoordinates = getActiveCoordinateSet(pocketDimension, xRange, yRange)

    repeat (6) {
        xRange.increaseRange()
        yRange.increaseRange()
        zRange.increaseRange()
        wRange.increaseRange()

        activeCoordinates = getNewStateOfActiveCoordinates(xRange, yRange, zRange, wRange, activeCoordinates)
    }

    println("Final number of active coordinates: ${activeCoordinates.size}")
}

private fun getNewStateOfActiveCoordinates(
    xRange: CoordinateRange,
    yRange: CoordinateRange,
    zRange: CoordinateRange,
    wRange: CoordinateRange,
    activeCoordinates: HashSet<Coordinate>
): HashSet<Coordinate> {
    val newCoordinateActivityMap = hashSetOf<Coordinate>()
    useCoordinatesInRanges(xRange, yRange, zRange, wRange) { x, y, z, w ->
        val nearbyActivePointCount = countOfNearbyActivePoints(activeCoordinates, x, y, z, w)
        if (activeCoordinates.contains(Coordinate(x, y, z, w))) {
            if (nearbyActivePointCount in 2..3) newCoordinateActivityMap.add(Coordinate(x, y, z, w))
        } else {
            if (nearbyActivePointCount == 3) newCoordinateActivityMap.add(Coordinate(x, y, z, w))
        }
    }
    return newCoordinateActivityMap
}

private fun countOfNearbyActivePoints(
    activeCoordinates: HashSet<Coordinate>,
    x: Int,
    y: Int,
    z: Int,
    w: Int
): Int {
    var countOfActivePoints = 0
    useCoordinatesInRanges(
        CoordinateRange(x - 1, x + 1),
        CoordinateRange(y - 1, y + 1),
        CoordinateRange(z - 1, z + 1),
        CoordinateRange(w - 1, w + 1)
    ) {
        x1, y1, z1, w1 -> if (activeCoordinates.contains(Coordinate(x1, y1, z1, w1))) countOfActivePoints += 1
    }

    if (activeCoordinates.contains(Coordinate(x, y, z, w))) countOfActivePoints -= 1
    return countOfActivePoints
}
