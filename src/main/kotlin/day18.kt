import utils.readInputBlock

/** [https://adventofcode.com/2016/day/18] */
class Traps : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val first = readInputBlock("18.txt")
        return generateSequence(first) { previous ->
            previous.indices.map { index ->
                val left = previous.getOrElse(index - 1) { '.' } == '^'
                val center = previous[index] == '^'
                val right = previous.getOrElse(index + 1) { '.' } == '^'
                if ((left && center && !right) || (!left && center && right) || (left && !center && !right) || (!left && !center && right)) '^' else '.'
            }.joinToString("")
        }.take(if (part2) 400000 else 40).sumBy { it.count { c -> c == '.' } }
    }
}

fun main() {
    println(Traps().run(part2 = false))
}