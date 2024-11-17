import java.io.File

fun Int.to16BitUShort(): UShort {
    require(this in 0..65535) { "Value must be between 0 and 65535." }
    return this.toUShort()
}

class BitBro(val left: String? = null, val operator: String? = null, val right: String, val result: String) {
    val nonNullAttributeCount: Int
        get() = listOf(left, operator, right, result).count { it != null }

    // Override the toString() method
    override fun toString(): String {
        return StringBuilder().apply {
            append("BitBro Details:\n")
            append("Left: $left\n")
            append("Operator: $operator\n")
            append("Right: $right\n")
            append("Result: $result\n")
            append("Non-null attributes count: $nonNullAttributeCount\n")
        }.toString()
    }

    private fun computeResult(left: Int?, right: Int): Int {
        return if (left != null) {
            when (operator) {
                "RSHIFT" -> left shr right
                "LSHIFT" -> left shl right
                "XOR" -> left xor right
                "AND" -> left and right
                "OR" -> left or right
                else -> throw Exception("Fucked if I know!")
            }
        } else { // has to be NOT
            return right.to16BitUShort().inv().toInt()
        }
    }

    fun formattedResult(left: Int?, right: Int): Pair<String, Int> {
        return result to computeResult(left, right)
    }
}

fun findStartPoint(bitBros: List<BitBro>): BitBro {
    return bitBros.first{ it -> it.nonNullAttributeCount == 3 && it.right.toString().first().isDigit()}
}


fun q7partA(data: String): Unit {
    return
}
// [0, ->, c]
// c LSHIFT 1 -> t
fun main(args: Array<String>) {
    val filename = "src/q7_input.txt"
    val data = File(filename).readLines()
    val processedEquations = data.map {
        line ->
        val splits = line.split(" ")
        when (splits.size) {
            5 -> BitBro(left = splits.first(), operator = splits[1], right = splits[2], result = splits.last())
            4 -> BitBro(operator = splits.first(), right = splits[1], result = splits.last())
            3 -> BitBro(right = splits.first(), result = splits.last())
            else -> {
                throw Exception("Fuck me dead cunt!")
            }
        }
    }
    val startPt = findStartPoint(processedEquations)
    var count = 0
    var left: String? = null
    var right = startPt.right
    var result = startPt.result
    // [0, ->, c] -> right = '0', result = 'c'
    // c LSHIFT 1 -> t
    // putting above in formattedResult() ->
    while (count < processedEquations.size) {
        val relevantEq = processedEquations.first { eq -> eq.left == result || eq.right == result }
        val (varName, varVal) = relevantEq.formattedResult(left?.toIntOrNull(), right.toInt())  // Pair<String, Int>
        left = varVal.toString()

        count++
    }

}