import utils.Coordinate
import utils.readInputLines

/** [https://adventofcode.com/2016/day/2] */
class Code : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val keypad = if (part2) listOf("XX1XX", "X234X", "56789", "XABCX", "XXDXX") else listOf("123", "456", "789")

        val password = readInputLines("2.txt").runningFold(
            if (part2) Coordinate(0, 2) else Coordinate(1, 1)
        ) { last, instructions ->
            instructions.fold(last) { previous, direction ->
                val next = when (direction) {
                    'U' -> previous.up(offset = true)
                    'L' -> previous.left()
                    'R' -> previous.right()
                    'D' -> previous.down(offset = true)
                    else -> throw IllegalArgumentException()
                }
                if (next.y in keypad.indices && keypad[next.y].getOrElse(next.x) { 'X' } != 'X') next else previous
            }
        }

        return password.drop(1).map { keypad[it.y][it.x] }.joinToString("")
    }
}

fun main() {
    println(Code().run(part2 = true))
}