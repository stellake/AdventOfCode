package dec3

import java.io.File

fun main() {
    val mountainSections = getMountainSections()
    calculateNumberOfTrees(mountainSections, 3, 1)
}

fun getMountainSections(): List<String> {
    val mountainSection = mutableListOf<String>()
    File("C:/Work/AdventOfCode/src/dec3/Dec3Input.txt").useLines {
            lines -> lines.forEach { line -> mountainSection.add(line)
    }}
    return mountainSection.toList()
}

fun calculateNumberOfTrees(mountainSections: List<String>, xIncrement: Int, yIncrement: Int): Int {
    var treeCount = 0
    var xCoordinate = 0
    var yCoordinate = 0
    mountainSections.forEachIndexed { index, section ->
        if (index == yCoordinate) {
            if (coordinateHasTree(section, xCoordinate)) treeCount += 1
            xCoordinate += xIncrement
            yCoordinate += yIncrement
        }
    }
    println("The final tree count is: $treeCount")
    return treeCount
}

fun coordinateHasTree(mountainSection: String, coordinate: Int): Boolean {
    val sectionWidth = mountainSection.count()
    return mountainSection[coordinate % sectionWidth] == '#'
}
