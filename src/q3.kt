import java.io.File

fun updatePosition(position: Pair<Int, Int>, direction: Char): Pair<Int, Int> {
    return when (direction) {
        '<' -> Pair(position.first - 1, position.second)
        '>' -> Pair(position.first + 1, position.second)
        '^' -> Pair(position.first, position.second + 1)
        'v' -> Pair(position.first, position.second - 1)
        else -> position  // Return the same position if the direction is unrecognized
    }
}

fun q3part1(pathway: CharArray): Set<Pair<Int, Int>> {
    val initialPos = Pair(0, 0)
    var newPos = initialPos
    val uniqueHouses = mutableSetOf(initialPos)
    for (char in pathway) {
        newPos = updatePosition(newPos, char)
        uniqueHouses += newPos
    }
    return uniqueHouses
}

fun q3part2(pathway: CharArray): Set<Pair<Int, Int>> {
    val (santaDirs, roboSantaDirs) = pathway
        .withIndex().partition { it.index % 2 == 0 }
    val santaHouses = q3part1(santaDirs.map { it.value }.toCharArray())
    val roboSantaHouses = q3part1(roboSantaDirs.map { it.value }.toCharArray())
    return santaHouses + roboSantaHouses
}

fun main(args: Array<String>) {
    val filename = "src/q3_input.txt"
    val text = File(filename).readText().toCharArray()
    val uniqueHouses = q3part1(text)
    println("The number of unique houses visited are ${uniqueHouses.size}")

    val combinedUniqueHouses = q3part2(text)
    println("The combined unique houses of Santa and Robo Santa are ${combinedUniqueHouses.size}")
}