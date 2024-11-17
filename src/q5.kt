import java.io.File

fun hasDoubles(line: String): Boolean {
    return line.windowed(2, 1, false).any { it[0] == it[1] }
}

fun doesNotHaveSubstrings(line: String): Boolean {
    return !("ab" in line || "cd" in line || "pq" in line || "xy" in line)
}

fun containsAtLeastThreeVowels(line: String): Boolean {
    val lineWithoutVowels = line.filter {
        (it != 'a') && (it != 'e') && (it != 'i') && (it != 'o') && (it != 'u')
    }
    return line.length - lineWithoutVowels.length >= 3
}

fun isNiceStringPartA(line: String): Boolean {
    return hasDoubles(line) && doesNotHaveSubstrings(line) && containsAtLeastThreeVowels(line)
}

/*
 It contains at least one letter which repeats with exactly one letter between them, like: xyx, abcdefeghi (efe), or
 even aaa.
 */
fun repeatsWithOneLetterBetween(line: String): Boolean {
    return line.windowed(3, 1, false).any{it.first() == it.last()}
}

fun containsPairTwice(line: String): Boolean {
    val allPairs = line.windowed(2, 1, false)
    return allPairs.withIndex().any { (i, pair) ->
        (i + 2..<allPairs.size).any { j -> allPairs[j] == pair }
    }
}

fun isNiceStringPartB(line: String): Boolean {
    return repeatsWithOneLetterBetween(line) && containsPairTwice(line)
}

fun main(args: Array<String>) {
    val filename = "src/q5_input.txt"
    val data = File(filename).readLines()
    val niceDataA = data.filter { isNiceStringPartA(it) }
    println("The number of nice strings are ${niceDataA.size}")

    val niceDataB = data.filter { isNiceStringPartB(it) }
    println("The number of nice strings are ${niceDataB.size}")
}