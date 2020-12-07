package dec7

import java.io.File

const val BAG_COLOUR = "shiny gold"
const val NO_OTHERS = "no other"

fun main() {
    val bagRuleMap = getBagRules()
    var bagCombinations = 0
    bagRuleMap.forEach { (_, values) ->
        val anyBagHoldsGoldenBag = values.any { bag -> checkIfBagHoldsGolden(bag.key, bagRuleMap) }
        if (values.contains(BAG_COLOUR) || anyBagHoldsGoldenBag) {
            bagCombinations += 1
        }
    }
    println("Bag combinations: $bagCombinations")
}

private fun checkIfBagHoldsGolden(bagType: String, bagRuleMap: Map<String, Map<String, Long>>): Boolean {
    return when (bagType) {
        BAG_COLOUR -> true
        NO_OTHERS -> false
        else -> bagRuleMap[bagType]?.any { bag -> checkIfBagHoldsGolden(bag.key, bagRuleMap) } ?: false
    }
}

fun getBagRules(): Map<String, Map<String, Long>> {
    val bagRuleMap = mutableMapOf<String, Map<String, Long>>()
    File("C:/Work/AdventOfCode/src/dec7/Dec7Input.txt").useLines {
            lines -> lines.forEach { line -> updateBagMap(bagRuleMap, line) }
    }
    return bagRuleMap
}

private fun updateBagMap(bagRuleMap: MutableMap<String, Map<String, Long>>, line: String) {
    val ruleElements = line.split(" bags contain ")
    val key = ruleElements[0]
    val value = mutableMapOf<String, Long>()
    ruleElements[1]
        .split(" bags, ", " bag, ", " bag.", " bags.")
        .filter { v -> v != "" }
        .forEach { v -> value[v.getColourFrequency().first] = v.getColourFrequency().second }
    bagRuleMap[key] = value
}

private fun String.getColourFrequency(): Pair<String, Long> {
    val elements = this.split(" ")
    val numberOfColour = if (elements[0] == "no") 0L else elements[0].toLong()
    val startIndex = if (elements[0] == "no") 0 else 1
    return Pair(
        elements.subList(startIndex, elements.size).joinToString(" "),
        numberOfColour
    )
}
