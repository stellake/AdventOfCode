package dec8

fun main() {
    val bootInstructions = getBootInstructions()

    var indexToChange = 0
    var hasTerminatedCorrectly = false

    while (!hasTerminatedCorrectly) {
        val bootInstructionsCopy = bootInstructions.toMutableList()
        val instructionToChange = bootInstructionsCopy[indexToChange]
        val instruction = instructionToChange.instruction

        if (instruction == "jmp" || instruction == "nop") {
            val newInstructionToUse = if (instruction == "jmp") "nop" else "jmp"
            bootInstructionsCopy[indexToChange] = instructionToChange.copy(instruction = newInstructionToUse)
            val result = executeInstructions(bootInstructionsCopy)
            println("Result after instructions execution: $result")
            if (result.terminatedCorrectly) {
                println("Value in the accumulator: ${result.valueInAccumulator}")
                hasTerminatedCorrectly = true
            }
        }
        indexToChange += 1
    }
}
