import java.io.File
import kotlin.system.measureTimeMillis

fun getStartAndEndPoints(line: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    val regex = "\\d+".toRegex()
    val numbers = regex.findAll(line).map { it.value.toInt() }.toList()
    val startPt = numbers[0] to numbers[1]
    val endPt = numbers[2] to numbers[3]
    return startPt to endPt
}

fun getRelevantLights(line: String): Set<Pair<Int, Int>>  {
    val (startPt, endPt) = getStartAndEndPoints(line)
    val relevantLights = mutableSetOf<Pair<Int, Int>>()
    for (i in startPt.first..endPt.first) {
        for (j in startPt.second..endPt.second) {
            relevantLights.add(Pair(i, j))
        }
    }
    return relevantLights
}

infix fun <T> Set<T>.xor(other: Set<T>): Set<T> {
    return (this union other) subtract (this intersect other)
}

fun q6part1(): Set<Pair<Int, Int>> {
    val filename = "src/q6_input.txt"
    var lights: Set<Pair<Int, Int>> = mutableSetOf()
    File(filename).bufferedReader().useLines { lines ->
        lines.forEach { line ->
            val relevantLights = getRelevantLights(line)
            when {
                "toggle" in line -> lights = lights xor relevantLights
                "on" in line -> lights = lights union relevantLights
                "off" in line -> lights = lights subtract relevantLights
                else -> println("Dunno")
            }
        }
    }
    println("The number of lights left on by the end are: ${lights.size}")
    return lights
}

data class Light(val x: Int, val y: Int, var brightness: Int) {
    fun toggleBrightness(command: String): Unit {
        when {
            "toggle" in command -> this.brightness += 2
            "on" in command -> this.brightness += 1
            "off" in command -> this.brightness = (this.brightness - 1).coerceAtLeast(0)
        }
    }
}

fun q6part2(): MutableMap<Pair<Int, Int>, Light> {
    val (width, height) = Pair(1000, 1000)
    val initialBrightness = 0
    val lights = (0..< width * height).associate { index ->
        val x = index % width
        val y = index / width
        (x to y) to Light(x, y, initialBrightness)
    }.toMutableMap()
    val filename = "src/q6_input.txt"
    val instructions = File(filename).readLines()
    instructions.forEach {
        val (startPt, endPt) = getStartAndEndPoints(it)
        for (i in startPt.first..endPt.first) {
            for (j in startPt.second..endPt.second) {
                lights[Pair(i, j)]?.toggleBrightness(it)
            }
        }
    }
    return lights
}

fun main(args: Array<String>) {
    val lights = q6part2()
    val totalBrightness = lights.values.sumOf{
        it.brightness
    }
    println("The total brightness is $totalBrightness")
}