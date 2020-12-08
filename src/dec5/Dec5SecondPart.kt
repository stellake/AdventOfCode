package dec5

fun main() {
    val boardingPasses = getBoardingPasses()

    val boardingPassIds = boardingPasses.map { p -> p.getId() }
    val maxBoardingPassId = boardingPassIds.max()!!
    val minBoardingPassId = boardingPassIds.min()!!

    var boardingPassIdToCheck = minBoardingPassId.plus(1)
    while (boardingPassIdToCheck <= maxBoardingPassId) {
        val boardingPassExists = boardingPassIds.contains(boardingPassIdToCheck)
        if (!boardingPassExists) {
            println("Boarding pass with id $boardingPassIdToCheck doesn't exist!")
            return
        }
        boardingPassIdToCheck += 1
    }
}
