import utils.readInputLines
import kotlin.math.max

/** [https://adventofcode.com/2016/day/20] */
class Firewall : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val ranges = readInputLines("20.txt").map {
            val (from, to) = it.split("-")
            from.toLong() to to.toLong()
        }.sortedBy { it.first }

        val allowed = mutableSetOf<Long>()
        ranges.reduce { total, next ->
            if (next.first > total.second + 1) allowed.addAll(total.second + 1 until next.first)
            total.first to max(total.second, next.second)
        }
        return if (part2) allowed.size else allowed.minOrNull()!!
    }
}

fun main() {
    println(Firewall().run(part2 = true))
}