import utils.readInputLines

/** [https://adventofcode.com/2016/day/3] */
class Triangles : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val triangles = readInputLines("3.txt").map { it.trim().split(Regex("\\s+")).map(String::toInt) }
        return if (part2) triangles.windowed(3, 3).sumBy { triples ->
            (0..2).count { index -> triples.map { it[index] }.isValid() }
        }
        else triangles.count { it.isValid() }
    }

    private fun List<Int>.isValid() = sorted().let { it[0] + it[1] > it[2] }
}

fun main() {
    println(Triangles().run(part2 = true))
}