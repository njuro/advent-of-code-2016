import utils.Coordinate
import utils.Direction
import utils.readInputBlock

/** [https://adventofcode.com/2016/day/1] */
class Navigation : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val path = readInputBlock("1.txt").split(", ")
            .runningFold(listOf(Coordinate(0, 0) to Direction.UP)) { previous, next ->
                val (coordinate, direction) = previous.last()
                val newDirection = if (next.first() == 'L') direction.turnLeft() else direction.turnRight()
                (1..next.drop(1).toInt()).runningFold(coordinate)
                { position, _ -> position.move(newDirection) }.drop(1).map { it to newDirection }
            }.flatten().map { it.first }
        val target = if (part2) path.first { path.indexOf(it) != path.lastIndexOf(it) } else path.last()
        return target.distanceToCenter()
    }
}

fun main() {
    println(Navigation().run(part2 = true))
}