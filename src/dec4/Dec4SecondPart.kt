package dec4

fun main() {
    val passports = getPassports()
    val validPassportCount = passports.count { passport -> passport.isValid() }
    println("Valid passport count: $validPassportCount")
}

private fun Passport.isValid(): Boolean {
    val correctBirthYear = birthYear.correctYearFormatWithValueBetween(1920, 2002)
    val correctIssueYear = issueYear.correctYearFormatWithValueBetween(2010, 2020)
    val correctExpirationYear = expirationYear.correctValueBetween(2020, 2030)
    val correctHeight = height != null && isCorrectHeight(height!!)
    val correctHairColour = hairColour != null && hairColour!!.matches(Regex("^#(?:[0-9a-fA-F]{3}){1,2}$"))
    val validEyeColours = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    val correctEyeColour = validEyeColours.contains(eyeColour)
    val correctPassportId = passportId != null && passportId!!.count() == 9
    return correctBirthYear && correctIssueYear && correctExpirationYear && correctHeight && correctHairColour && correctEyeColour && correctPassportId
}

private fun isCorrectHeight(height: String): Boolean {
    if (height.count() < 3) return false

    val unit = height.substring(height.count() - 2, height.count())
    val number =  height.substring(0, height.count() - 2).toInt()
    return when (unit) {
        "cm" -> number in 150..193
        "in" -> number in 59..76
        else -> false
    }
}

private fun Int?.correctYearFormatWithValueBetween(minYear: Int, maxYear: Int): Boolean {
    return this.correctValueBetween(minYear, maxYear) && this.toString().count() == 4
}

private fun Int?.correctValueBetween(minYear: Int, maxYear: Int): Boolean {
    return this != null && this >= minYear && this <= maxYear
}
