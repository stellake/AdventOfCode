package dec4

import java.io.File

data class Passport(
    var birthYear: Int? = null,
    var issueYear: Int? = null,
    var expirationYear: Int? = null,
    var height: String? = null,
    var hairColour: String? = null,
    var eyeColour: String? = null,
    var passportId: String? = null,
    var countryId: String? = null
) {
    fun hasAllRequiredFields(): Boolean {
        return birthYear != null && issueYear != null && expirationYear != null && height != null && hairColour != null && eyeColour != null && passportId != null
    }
}

fun main() {
    val passports = getPassports()
    val validPassportCount = passports.count { passport -> passport.hasAllRequiredFields() }
    println("Valid passport count: $validPassportCount")
}

fun getPassports(): List<Passport> {
    val passportStrings = mutableListOf<String>()
    File("C:/Work/AdventOfCode/src/dec4/Dec4Input.txt").useLines {
        lines -> lines.forEach { line -> passportStrings.add(line)}
    }
    return passportStrings.joinToString("-").split("--").map { p -> buildPassportFromPassportLine(p) }
}

fun buildPassportFromPassportLine(passportLine: String): Passport {
    val passportDetailPairs = passportLine.split(" ", "-")
    val passport = Passport()
    passportDetailPairs.forEach { passportDetail ->
        val keyValueString = passportDetail.split(":")
        val key = keyValueString[0]
        val value = keyValueString[1]
        updatePassportField(passport, key, value)
    }
    return passport
}

fun updatePassportField(passport: Passport, key: String, value: String) {
    when (key) {
        "byr" -> passport.birthYear = value.toInt()
        "iyr" -> passport.issueYear = value.toInt()
        "eyr" -> passport.expirationYear = value.toInt()
        "hgt" -> passport.height = value
        "hcl" -> passport.hairColour = value
        "ecl" -> passport.eyeColour = value
        "pid" -> passport.passportId = value
        "cid" -> passport.countryId = value
    }
}
