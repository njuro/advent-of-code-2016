import utils.readInputLines

/** [https://adventofcode.com/2016/day/7] */
class Addresses : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        return readInputLines("7.txt").count { address ->
            val parts = address.split(Regex("[\\[\\]]"))
                .withIndex().groupBy { it.index % 2 == 0 }.mapValues { entry -> entry.value.map { it.value } }
            if (part2) {
                val abaBlocks = parts[true]!!.flatMap { it.getABA() }
                abaBlocks.any { parts[false]!!.any { block -> block.hasBAB(it) } }
            } else {
                parts[true]!!.any { it.hasABBA() } && parts[false]!!.none { it.hasABBA() }
            }
        }
    }

    private fun String.hasABBA() = windowed(4, 1, false)
        .any { it[0] != it[1] && it[1] == it[2] && it[0] == it[3] }

    private fun String.getABA() = windowed(3, 1, false)
        .filter { it[0] == it[2] && it[0] != it[1] }

    private fun String.hasBAB(aba: String) = windowed(3, 1, false)
        .any { it[0] == aba[1] && it[1] == aba[0] && it[2] == aba[1] }
}

fun main() {
    println(Addresses().run(part2 = true))
}