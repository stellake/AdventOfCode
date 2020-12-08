package dec5

import java.io.File

data class BoardingPass(
    val row: Int, // 0 - 127
    val seat: Int // 0 - 7
) {
    fun getId() = row * 8 + seat
}

fun main() {
    val boardingPasses = getBoardingPasses()
    val boardingPassIds = boardingPasses.map { p -> p.getId() }
    val maxBoardingPassId = boardingPassIds.max()
    println("The max boarding pass id is $maxBoardingPassId")
}

fun getBoardingPasses(): List<BoardingPass> {
    val boardingPasses = mutableListOf<BoardingPass>()
    File("C:/Work/AdventOfCode/src/dec5/Dec5Input.txt").useLines {
            lines -> lines.forEach { line -> boardingPasses.add(buildBoardingPass(line)) }
    }
    return boardingPasses
}

private fun buildBoardingPass(line: String): BoardingPass {
    if (line.count() != 10) {
        throw Error("Incorrect boarding pass format")
    }

    val rowString = line.substring(0, 7)
    val seatString = line.substring(7, 10)

    return BoardingPass(
        processRowString(rowString),
        processSeatString(seatString)
    )
}

private fun processRowString(rowString: String): Int {
    val rowInBinary = rowString.replace("F", "0").replace("B", "1")
    return Integer.parseInt(rowInBinary, 2);
}

private fun processSeatString(seatString: String): Int {
    val seatInBinary = seatString.replace("L", "0").replace("R", "1")
    return Integer.parseInt(seatInBinary, 2);
}
