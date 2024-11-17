import java.io.File

fun prismSurfaceArea(length: Int, width: Int, height: Int): Int {
    return 2 * length * width + 2 * width * height + 2 * height * length
}

fun theExtraBit(length: Int, width: Int, height: Int): Int {
    return arrayOf(length, width, height).sorted().dropLast(1).reduce { acc, num -> acc * num }
}

fun wrappingPaperRequired(length: Int, width: Int, height: Int): Int {
    return prismSurfaceArea(length, width, height) + theExtraBit(length, width, height)
}

fun q2part1(filename: String): Int {
    return File(filename).readLines().sumOf {
        line -> val (length, width, height) = line.split("x").map { it.toInt() }
        wrappingPaperRequired(length, width, height)
    }
}

fun perimeterRibbon(length: Int, width: Int, height: Int): Int {
    return arrayOf(length, width, height)
        .sorted()
        .dropLast(1)
        .reduce {
                acc, i -> 2 * (acc + i)
        }
}

fun perimeterBow(length: Int, width: Int, height: Int): Int {
    return length * width * height
}

fun totalPerimeter(length: Int, width: Int, height: Int): Int {
    return perimeterBow(length, width, height) + perimeterRibbon(length, width, height)
}

fun q2part2(filename: String): Int {
    return File(filename).readLines().sumOf {
            line -> val (length, width, height) = line.split("x").map { it.toInt() }
        totalPerimeter(length, width, height)
    }
}

fun main(args: Array<String>) {
    val filename = "src/q2_input.txt"

    val totalRequiredWrappingPaper = q2part1(filename)
    println("The total required wrapping paper is $totalRequiredWrappingPaper square feet.")

    val totalRequiredRibbon = q2part2(filename)
    println("The total perimeter of ribbon required in feet is: $totalRequiredRibbon")
}