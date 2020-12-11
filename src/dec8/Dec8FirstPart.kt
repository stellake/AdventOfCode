package dec8

import java.io.File

data class BootInstruction(
    val instruction: String,
    val number: Int
)

fun main() {
    val bootInstructions = getBootInstructions()
    val result = executeInstructions(bootInstructions)
    println("Value in accumulator when terminated: ${result.valueInAccumulator}")
}

data class InstructionExecutionResult(
    val terminatedCorrectly: Boolean,
    val valueInAccumulator: Int
)

fun executeInstructions(bootInstructions: List<BootInstruction>): InstructionExecutionResult {
    var lineOfCode = 0
    var valueInAccumulator = 0
    val hasLineAlreadyBeenExecutedMap = mutableMapOf<Int, Boolean>()

    while (true) {
        val executeReturn = executeInstruction(bootInstructions[lineOfCode])
        valueInAccumulator += executeReturn.accChange
        val newLine = lineOfCode + executeReturn.changeInLine
        when {
            hasLineAlreadyBeenExecutedMap[newLine] == true -> return InstructionExecutionResult(false, valueInAccumulator)
            newLine == bootInstructions.size -> return InstructionExecutionResult(true, valueInAccumulator)
            else -> {
                hasLineAlreadyBeenExecutedMap[newLine] = true
                lineOfCode = newLine
            }
        }
    }
}

data class InstructionExecutionReturn(
    val changeInLine: Int,
    val accChange: Int
)

fun executeInstruction(bootInstruction: BootInstruction): InstructionExecutionReturn {
    return when (bootInstruction.instruction) {
        "acc" -> InstructionExecutionReturn(1, bootInstruction.number)
        "jmp" -> InstructionExecutionReturn(bootInstruction.number, 0)
        "nop" -> InstructionExecutionReturn(1, 0)
        else -> throw Error("Instruction not understood!")
    }
}

fun getBootInstructions(): List<BootInstruction> {
    val instructionList = mutableListOf<BootInstruction>()
    File("C:/Work/AdventOfCode/src/dec8/Dec8Input.txt").useLines {
            lines -> lines.forEach { line -> instructionList.add(buildBootInstruction(line)) }
    }
    return instructionList
}

private fun buildBootInstruction(line: String): BootInstruction {
    val parts = line.split(" ")
    return BootInstruction(parts[0], parts[1].toInt())
}
