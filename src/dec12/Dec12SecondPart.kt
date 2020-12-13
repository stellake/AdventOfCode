package dec12

import kotlin.math.abs
import kotlin.math.roundToInt

data class ShipAndWayPointNavigationState(
    var shipX: Int,
    var shipY: Int,
    var wayPointX: Int,
    var wayPointY: Int
) {
    fun getManhattanDistance(): Int {
        return abs(shipX) + abs(shipY)
    }

    fun moveShipTowardsWayPoint(numberOfTimes: Int) {
        shipX += numberOfTimes * wayPointX
        shipY += numberOfTimes * wayPointY
    }
}

fun main() {
    val navigationInstructions = getNavigationInstructions()
    val shipState = ShipAndWayPointNavigationState(0, 0, 10, 1)
    navigationInstructions.forEach { applyInstruction(shipState, it) }
    println("The Manhattan disctance is: ${shipState.getManhattanDistance()}")
}

private fun applyInstruction(shipAndWayPointState: ShipAndWayPointNavigationState, instruction: NavigationInstruction) {
    when (instruction.action) {
        'N' -> shipAndWayPointState.wayPointY += instruction.value
        'S' -> shipAndWayPointState.wayPointY -= instruction.value
        'E' -> shipAndWayPointState.wayPointX += instruction.value
        'W' -> shipAndWayPointState.wayPointX -= instruction.value
        'L' -> rotateWaypoint(shipAndWayPointState, instruction)
        'R' -> rotateWaypoint(shipAndWayPointState, instruction)
        'F' -> shipAndWayPointState.moveShipTowardsWayPoint(instruction.value)
        else -> throw Error("Can't recognise the action: ${instruction.action}")
    }
}

private fun rotateWaypoint(shipAndWayPointState: ShipAndWayPointNavigationState, instruction: NavigationInstruction) {
    val rotationToLeft = if (instruction.action == 'R') - instruction.value else instruction.value
    val rotationToLeftInRad = Math.toRadians(rotationToLeft.toDouble())

    val newWaypointX = shipAndWayPointState.wayPointX * kotlin.math.cos(rotationToLeftInRad) - shipAndWayPointState.wayPointY * kotlin.math.sin(rotationToLeftInRad)
    val newWaypointY = shipAndWayPointState.wayPointX * kotlin.math.sin(rotationToLeftInRad) + shipAndWayPointState.wayPointY * kotlin.math.cos(rotationToLeftInRad)

    shipAndWayPointState.wayPointX = newWaypointX.roundToInt()
    shipAndWayPointState.wayPointY = newWaypointY.roundToInt()
}
