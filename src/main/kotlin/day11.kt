import utils.readInputLines

/** [https://adventofcode.com/2016/day/11] */
class Elevator : AdventOfCodeTask {

    data class State(val steps: Int, val floor: Int, val map: List<Set<String>>)

    override fun run(part2: Boolean): Any {
        val divider = Regex(",?( and)? a ")
        val floors = readInputLines("11.txt").map {
            if ("nothing relevant" in it) mutableSetOf()
            else it.substringAfter("floor contains a ").dropLast(1).split(divider).toMutableSet()
        }
        if (part2) {
            floors[0].addAll(
                setOf(
                    "elerium generator", "elerium-compatible microchip",
                    "dilithium generator", "dilithium-compatible microchip"
                )
            )
        }
        val itemsCount = floors.sumBy { it.size }

        val queue = mutableListOf(State(0, 0, floors))
        val seen = mutableSetOf<String>()
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val hash = "${current.floor}${current.map.hash()}"
            if (hash in seen) {
                continue
            }
            seen.add(hash)
            val items = current.map[current.floor]

            val oneItem = items.map { setOf(it) }
            val twoItems = items.flatMap { first ->
                items.filter { it != first }.map { second -> setOf(first, second) }
            }.toSet()
            val candidates = oneItem + twoItems
            setOf(current.floor - 1, current.floor + 1).forEach outer@{ nextFloor ->
                if (nextFloor < 0 || nextFloor > 3) {
                    return@outer
                }

                candidates.forEach inner@{ moved ->
                    val nextMap = current.map.toMutableList()
                    nextMap[current.floor] = nextMap[current.floor] - moved
                    nextMap[nextFloor] = nextMap[nextFloor] + moved
                    if (!nextMap[current.floor].isValid() || !nextMap[nextFloor].isValid()) {
                        return@inner
                    }

                    if (nextFloor == 3 && nextMap[nextFloor].size == itemsCount) {
                        return current.steps + 1
                    }

                    queue.add(State(current.steps + 1, nextFloor, nextMap))
                }
            }
        }

        throw IllegalArgumentException()
    }

    private fun Set<String>.isValid() =
        none { it.endsWith("generator") } ||
            filter { it.endsWith("microchip") }.all { "${it.substringBefore("-")} generator" in this }

    private fun List<Set<String>>.hash() = joinToString("|") { it.sorted().map(String::last).joinToString("") }
}

fun main() {
    println(Elevator().run(part2 = true))
}