import utils.Coordinate
import utils.readInputLines

/** [https://adventofcode.com/2016/day/22] */
class Grid : AdventOfCodeTask {
    data class Node(val position: Coordinate, val used: Int, val capacity: Int, val percentage: Int)

    override fun run(part2: Boolean): Any {
        val pattern = Regex("/dev/grid/node-x(\\d+)-y(\\d+)\\s+(\\d+)T\\s+(\\d+)T\\s+\\d+T\\s+(\\d+)%")
        val nodes = readInputLines("22.txt").drop(2).map {
            val (x, y, capacity, used, percentage) = pattern.matchEntire(it)!!.destructured
            Node(Coordinate(x.toInt(), y.toInt()), used.toInt(), capacity.toInt(), percentage.toInt())
        }

        if (!part2) {
            return nodes.filter { it.used > 0 }.sumBy { a ->
                nodes.count { b ->
                    a.position != b.position && a.used <= (b.capacity - b.used)
                }
            }
        }

        val target = Coordinate(0, 0)
        val source = Coordinate(nodes.maxOf { node -> node.position.x } - 1, 0)
        val start = nodes.first { it.used == 0 }.position
        val available = nodes.filter { it.percentage < 90 }.map(Node::position).toSet()
        val queue = mutableListOf(Triple(start, false, 0))
        val seen = mutableSetOf<Coordinate>()
        var sequenceIndex = 0
        while (queue.isNotEmpty()) {
            val (current, sourceVisited, steps) = queue.removeFirst()
            if (!sourceVisited) {
                if (current in seen) {
                    continue
                }
                seen.add(current)
            } else if (current == target) {
                return steps + 1
            }

            val nextDirections = if (!sourceVisited)
                current.adjacent().values.filter { it in available }.toMutableList()
            else
                mutableListOf(
                    getNext(source).drop(sequenceIndex++).first()
                )

            if (source in nextDirections) {
                queue.clear()
                nextDirections.removeIf { it != source }
            }

            nextDirections.forEach { next ->
                queue.add(Triple(next, sourceVisited || next == source, steps + 1))
            }
        }

        throw IllegalArgumentException()
    }

    private fun getNext(position: Coordinate) = sequence {
        var current = position
        while (true) {
            current = current.right()
            yield(current)
            current = current.down(offset = true)
            yield(current)
            current = current.left()
            yield(current)
            current = current.left()
            yield(current)
            current = current.up(offset = true)
            yield(current)
        }
    }
}

fun main() {
    println(Grid().run(part2 = true))
}