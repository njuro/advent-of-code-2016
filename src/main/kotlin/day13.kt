import utils.Coordinate
import utils.readInputBlock

/** [https://adventofcode.com/2016/day/13] */
class Maze : AdventOfCodeTask {
    private val constant = readInputBlock("13.txt").toInt()

    override fun run(part2: Boolean): Any {
        val start = Coordinate(1, 1)
        val end = Coordinate(31, 39)
        val queue = mutableListOf(start to 0)
        val seen = mutableSetOf<Coordinate>()

        while (queue.isNotEmpty()) {
            val (current, steps) = queue.removeFirst()
            if (part2 && steps > 50) {
                continue
            }
            if (current == end) {
                return steps
            }
            seen.add(current)

            queue.addAll(
                current.adjacent().values
                    .filter { it.x >= 0 && it.y >= 0 && it !in seen && !it.isWall() }
                    .map { it to steps + 1 }
            )
        }

        return if (part2) seen.size else throw IllegalArgumentException()
    }

    private fun Coordinate.isWall() =
        (x * x + 3 * x + 2 * x * y + y + y * y + constant).toString(2).count { it == '1' } % 2 == 1
}

fun main() {
    println(Maze().run(part2 = true))
}