package dec12

import java.io.File
import kotlin.math.abs

data class ShipNavigationState(
    var baring: Int, // degrees from N - clockwise
    var x: Int,
    var y: Int
) {
    fun getManhattanDistance(): Int {
        return abs(x) + abs(y)
    }
}

fun main() {
    val navigationInstructions = getNavigationInstructions()
    val shipState = ShipNavigationState(90, 0, 0)
    navigationInstructions.forEach { applyInstruction(shipState, it) }
    println("The Manhattan distance is: ${shipState.getManhattanDistance()}")
}

private fun applyInstruction(shipState: ShipNavigationState, instruction: NavigationInstruction) {
    when (instruction.action) {
        'N' -> shipState.y += instruction.value
        'S' -> shipState.y -= instruction.value
        'E' -> shipState.x += instruction.value
        'W' -> shipState.x -= instruction.value
        'L' -> shipState.baring -= instruction.value
        'R' -> shipState.baring += instruction.value
        'F' -> goForward(shipState, instruction)
        else -> throw Error("Can't recognise the action: ${instruction.action}")
    }
}

private fun goForward(shipState: ShipNavigationState, instruction: NavigationInstruction) {
    val baringInRad = Math.toRadians(shipState.baring.toDouble())
    shipState.x += Math.multiplyExact(kotlin.math.sin(baringInRad).toInt(), instruction.value)
    shipState.y += Math.multiplyExact(kotlin.math.cos(baringInRad).toInt(), instruction.value)
}


data class NavigationInstruction(
    val action: Char,
    val value: Int
)

fun getNavigationInstructions(): List<NavigationInstruction> {
    return File("C:/Work/AdventOfCode/src/dec12/Dec12Input.txt").readLines()
        .map { buildNavigationInstruction(it) }
}

private fun buildNavigationInstruction(instructionString: String): NavigationInstruction {
    return NavigationInstruction(
        instructionString[0],
        instructionString.substring(1).toInt()
    )
}
