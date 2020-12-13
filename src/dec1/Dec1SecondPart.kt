package dec1

fun main() {
    val numbers = getNumbers()

    for (i in 0 until numbers.size - 2) {
        for (j in i + 1 until numbers.size - 1) {
            for (k in j + 1 until numbers.size) {
                calculateSumAndPrintIfCorrect(numbers[i], numbers[j], numbers[k])
            }
        }
    }
}

fun calculateSumAndPrintIfCorrect(firstNum: Int, secondNum: Int, thirdNum: Int) {
    if (secondNum + firstNum + thirdNum == 2020) {
        val product = secondNum * firstNum * thirdNum
        print("Product is : $product. The matching numbers are: $firstNum, $secondNum, $thirdNum")
    }
}
