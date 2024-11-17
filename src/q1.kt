import java.io.File

fun part1(text: String): Int {
    val (left, right) = text.partition { char -> char == '(' }
    return left.length - right.length
}

fun part2(text: String) = text.toCharArray().map {
        char -> if (char == '(') 1 else -1
    }.runningReduce { acc, value -> acc + value}.indexOf(-1)

fun main(args: Array<String>) {
    val text = File("src/q1_input.txt").readText()
    val level = part1(text)
    println("Santas level is $level")
    val p2 = part2(text) + 1
    println("The first time Santa gets below level 0 is at index $p2")
}