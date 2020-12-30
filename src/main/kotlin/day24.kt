import utils.Coordinate
import utils.permutations
import utils.readInputLines

/** [https://adventofcode.com/2016/day/24] */
class Ventilation : AdventOfCodeTask {

    private val maze = readInputLines("24.txt").flatMapIndexed { y, row ->
        row.mapIndexed { x, c -> Coordinate(x, y) to c }
    }.toMap().withDefault { '#' }
    private val locations =
        maze.filterValues { Character.isDigit(it) }.mapValues { Character.getNumericValue(it.value) }
    private val distances = locations.entries.associate { it.value to discoverPaths(it.key) }
    private val combinations = locations.values.filter { it != 0 }.permutations()

    override fun run(part2: Boolean): Any {
        return combinations.minOf {
            (listOf(0) + it + if (part2) listOf(0) else listOf())
                .zipWithNext { from, to -> distances[from]!![to]!! }.sum()
        }
    }

    private fun discoverPaths(start: Coordinate): Map<Int, Int> {
        val discovered = mutableMapOf<Int, Int>()
        val queue = mutableListOf(start to 0)
        val seen = mutableSetOf<Coordinate>()

        while (discovered.size != locations.size) {
            val (position, steps) = queue.removeFirst()
            if (position in seen) {
                continue
            }
            seen.add(position)
            val current = maze.getValue(position)
            if (current == '#') continue
            if (current.isDigit()) discovered[Character.getNumericValue(current)] = steps
            position.adjacent().values.forEach { next ->
                queue.add(next to steps + 1)
            }
        }

        return discovered
    }
}

fun main() {
    println(Ventilation().run(part2 = true))
}