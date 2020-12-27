import utils.Coordinate
import utils.readInputLines
import utils.toStringRepresentation

/** [https://adventofcode.com/2016/day/8] */
class Lights : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val width = 50
        val height = 6
        var lights = (0 until height).flatMap { y -> (0 until width).map { x -> Coordinate(x, y) to '.' } }.toMap()
        val pattern = Regex("(rect|rotate row|rotate column) (?:[yx]=)?(\\d+)(?:x| by )(\\d+)")
        
        readInputLines("8.txt").forEach { instruction ->
            val (operation, p1, p2) = pattern.matchEntire(instruction)!!.destructured
            val updated = lights.toMutableMap()
            when (operation) {
                "rect" -> lights.filterKeys { it.x < p1.toInt() && it.y < p2.toInt() }.keys.forEach {
                    updated[it] = '#'
                }
                "rotate row" -> lights.filterKeys { it.y == p1.toInt() }.keys.forEach {
                    updated[Coordinate(
                        (it.x + p2.toInt()) % width,
                        it.y
                    )] = lights[it]!!
                }
                "rotate column" -> lights.filterKeys { it.x == p1.toInt() }.keys.forEach {
                    updated[Coordinate(
                        it.x,
                        (it.y + p2.toInt()) % height
                    )] = lights[it]!!
                }
            }
            lights = updated
        }

        return if (part2) lights.toStringRepresentation(
            offsetCoordinates = true,
            separator = ""
        ) else lights.values.count { it == '#' }
    }
}

fun main() {
    println(Lights().run(part2 = true))
}