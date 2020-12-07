package dec7

fun main() {
    val bagRuleMap = getBagRules()
    var bagsWithinGoldenBag = 0L
    bagRuleMap[BAG_COLOUR]?.forEach { v ->
        if (v.key != NO_OTHERS) {
            bagsWithinGoldenBag += Math.multiplyExact(v.value, (1 + getNumberOfBagsInsideBag(v.key, bagRuleMap)))
        }
    }
    println("Bags with golden bag: $bagsWithinGoldenBag")
}

fun getNumberOfBagsInsideBag(bag: String, bagRuleMap: Map<String, Map<String, Long>>): Long {
    return if (bag == NO_OTHERS) 0L else {
        val numberOfBags = bagRuleMap[bag]
            ?.map { bagsInBag -> bagsInBag.value * (1 + getNumberOfBagsInsideBag(bagsInBag.key, bagRuleMap)) }
            ?.sum()
        numberOfBags ?: 0
    }
}
