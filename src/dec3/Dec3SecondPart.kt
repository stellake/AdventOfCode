package dec3

data class Case(
    val xIncrement: Int,
    val yIncrement: Int
)

fun main() {
    val mountainSections = getMountainSections()
    val cases = listOf(
        Case(1, 1),
        Case(3, 1),
        Case(5, 1),
        Case(7, 1),
        Case(1, 2)
    )
    val noOfTrees = cases.map { case -> calculateNumberOfTrees(mountainSections, case.xIncrement, case.yIncrement) }
    // Multiply exact used to avoid overflow
    val product = noOfTrees.fold(1L, { total, next -> Math.multiplyExact(total, next) })
    println("Product: $product")
}
