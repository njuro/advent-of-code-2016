import utils.readInputLines

/** [https://adventofcode.com/2016/day/10] */
class Microchips : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val inputPattern = Regex("value (\\d+) goes to (bot \\d+)")
        val transferPattern = Regex("bot \\d+ gives low to ((?:bot|output) \\d+) and high to ((?:bot|output) \\d+)")
        val instructions = readInputLines("10.txt")
        val contents = mutableMapOf<String, MutableSet<Int>>().withDefault { mutableSetOf() }

        instructions.filter { it.startsWith("value") }.forEach {
            val (value, bot) = inputPattern.matchEntire(it)!!.destructured
            contents[bot] = contents.getValue(bot).apply { add(value.toInt()) }
        }

        while (true) {
            if (!part2) {
                val result = contents.entries.find { it.value == mutableSetOf(61, 17) }
                if (result != null) {
                    return result.key.substringAfter("bot ").toInt()
                }
            }
            val full = contents.filter { it.value.size == 2 }.keys.firstOrNull() ?: break
            val instruction = instructions.first { it.startsWith("$full ") }
            val (low, high) = transferPattern.matchEntire(instruction)!!.destructured
            contents[low] = contents.getValue(low).apply { add(contents[full]!!.minOrNull()!!) }
            contents[high] = contents.getValue(high).apply { add(contents[full]!!.maxOrNull()!!) }
            contents[full] = mutableSetOf()
        }

        return (0..2).fold(1) { result, number -> result * contents["output $number"]!!.first() }
    }
}

fun main() {
    println(Microchips().run(part2 = true))
}