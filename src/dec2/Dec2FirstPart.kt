package dec2

import java.io.File

data class Password(
    val firstNumberRequirement: Int,
    val secondNumberRequirement: Int,
    val characterRequirement: String,
    val password: String
)

fun main() {
    val passwords = getPasswords()
    val numberOfCorrectPw = passwords.count { pw -> pw.isValid1() }
    print("No of correct pw: $numberOfCorrectPw")
}

fun getPasswords(): List<Password> {
    val passwords = mutableListOf<Password>()
    File("C:/Work/AdventOfCode/src/dec2/Dec2Input.txt").useLines {
            lines -> lines.forEach { line -> passwords.add(buildPasswordFromLine(line))
    }}
    return passwords.toList()
}

fun buildPasswordFromLine(line: String): Password {
    val parts = line.split("-", " ", ": ")
    if (parts.size > 4) {
        println("Issue with processing line: $line")
    }

    return Password(
        parts[0].toInt(),
        parts[1].toInt(),
        parts[2],
        parts[3]
    )
}

fun Password.isValid1(): Boolean {
    val charCountInPassword = password.toCharArray().count { charInPw -> charInPw == characterRequirement[0] }
    return charCountInPassword in firstNumberRequirement..secondNumberRequirement
}
