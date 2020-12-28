import utils.readInputLines

/** [https://adventofcode.com/2016/day/15] */
class Discs : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val pattern = Regex("Disc #\\d+ has (\\d+) positions; at time=0, it is at position (\\d+)\\.")
        val discs = readInputLines("15.txt").map {
            val (positions, starting) = pattern.matchEntire(it)!!.destructured
            starting.toInt() to positions.toInt()
        }.toMutableList()

        if (part2) {
            discs.add(0 to 11)
        }

        return generateSequence(0) { it + 1 }.first {
            discs.withIndex().all { disc -> (disc.index + 1 + disc.value.first + it) % disc.value.second == 0 }
        }
    }
}

fun main() {
    println(Discs().run(part2 = true))
}