import utils.Coordinate
import utils.Direction
import utils.readInputBlock
import java.security.MessageDigest
import kotlin.math.max

/** [https://adventofcode.com/2016/day/17] */
class Vault : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val input = readInputBlock("17.txt")
        val queue = mutableListOf(Triple(Coordinate(0, 0), "", 0))
        val directions =
            listOf(Direction.UP to "U", Direction.DOWN to "D", Direction.LEFT to "L", Direction.RIGHT to "R")
        var maxSteps = 0
        while (queue.isNotEmpty()) {
            val (position, path, steps) = queue.removeFirst()
            if (position == Coordinate(3, 3)) {
                if (part2) {
                    maxSteps = max(maxSteps, steps)
                    continue
                } else return path
            }
            val opened = md5("$input$path").take(4).mapIndexedNotNull { index, c -> if (c in 'b'..'f') index else null }
            directions.filterIndexed { index, _ -> index in opened }
                .map { position.move(it.first, offset = true) to it.second }
                .filter { it.first.x in 0..3 && it.first.y in 0..3 }
                .forEach { queue.add(Triple(it.first, path + it.second, steps + 1)) }
        }

        return if (part2) maxSteps else throw IllegalArgumentException()
    }

    companion object {
        private val md5 = MessageDigest.getInstance("MD5")
    }

    private fun md5(str: String) = md5.digest(str.toByteArray()).joinToString("") { "%02x".format(it) }
}

fun main() {
    println(Vault().run(part2 = true))
}