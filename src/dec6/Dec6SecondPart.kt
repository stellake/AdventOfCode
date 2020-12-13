package dec6

fun main() {
    val detailsOfGroups = getGroupInformation()
    val sameAnswers = getNumberOfSameAnswers(detailsOfGroups)
    println("Same answers: $sameAnswers")
}

private fun getNumberOfSameAnswers(detailsOfGroups: List<GroupInformation>): Int {
    var sameAnswers = 0
    detailsOfGroups.forEach { groupDetails ->
        groupDetails.answerBreakdown.values.forEach { value ->
            if (value == groupDetails.humanCount) {
                sameAnswers += 1
            }
        }
    }
    return sameAnswers
}
