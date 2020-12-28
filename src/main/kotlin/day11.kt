import utils.readInputLines

/** [https://adventofcode.com/2016/day/11] */
class Day11 : AdventOfCodeTask {

    data class State(val steps: Int, val floor: Int, val map: List<Set<String>>)

    override fun run(part2: Boolean): Any {
        val input = readInputLines("11.txt")
        val floors = listOf(setOf("TG", "TM", "PG", "SG"), setOf("PM", "SM"), setOf("QG", "QM", "RG", "RM"), setOf())
        // val floors = listOf(setOf("HM", "LM"), setOf("HG"), setOf("LG"), setOf())

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

            val oneItems = items.map { setOf(it) }
            val twoItems = items.flatMap { first ->
                items.filter { it != first }.map { second -> setOf(first, second) }
            }.toSet()
            val candidates = oneItems + twoItems
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

                    if (nextFloor == 3 && nextMap[nextFloor].size == 10) {
                        return current.steps
                    }

                    queue.add(State(current.steps + 1, nextFloor, nextMap))
                }
            }
        }

        return -1
    }

    private fun Set<String>.isValid() =
        none { it.endsWith("G") } || filter { it.endsWith("M") }.all { "${it.first()}G" in this }

    private fun List<Set<String>>.hash() = joinToString("|") { it.joinToString("") }
}

fun main() {
    println(Day11().run(part2 = false))
}