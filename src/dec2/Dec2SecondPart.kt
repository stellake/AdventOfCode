package dec2

fun main() {
    val passwords = getPasswords()
    val numberOfCorrectPw = passwords.count { pw -> pw.isValid2() }
    print("No of correct pw: $numberOfCorrectPw")
}

fun Password.isValid2(): Boolean {
    val firstRequirementMet = password[firstNumberRequirement - 1] == characterRequirement[0]
    val secondRequirementMet = password[secondNumberRequirement - 1] == characterRequirement[0]
    return firstRequirementMet xor secondRequirementMet
}
