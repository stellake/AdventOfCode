package dec6

import java.io.File

data class GroupInformation(
    val humanCount: Int,
    val answerBreakdown: Map<Char, Int>
)

fun main() {
    val groupAnswers = getGroupInformation()
    val differentAnswers = groupAnswers.map { group -> group.answerBreakdown.size }.sum()
    println("Number of different answers: $differentAnswers")
}

fun getGroupInformation(): List<GroupInformation> {
    val answersPerGroup = File("C:/Work/AdventOfCode/src/dec6/Dec6Input.txt")
        .readText()
        .split(System.lineSeparator().repeat(2))

    return answersPerGroup.map { buildGroupInformation(it) }
}

private fun buildGroupInformation(groupAnswers: String): GroupInformation {
    val answersPerPerson = groupAnswers.split(System.lineSeparator())
    val answerCount = mutableMapOf<Char, Int>()

    answersPerPerson.forEach { answers ->
        answers.forEach { answerCount.increaseAnswerCount(it) }
    }

    return GroupInformation(answersPerPerson.size, answerCount)
}

private fun MutableMap<Char, Int>.increaseAnswerCount(letter: Char) {
    this[letter] = this[letter]?.plus(1) ?: 1
}
