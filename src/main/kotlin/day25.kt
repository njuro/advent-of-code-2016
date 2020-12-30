import utils.readInputLines

/** [https://adventofcode.com/2016/day/25] */
class Signal : AdventOfCodeTask {

    private val instructions = readInputLines("25.txt").map {
        val (operation, arguments) = it.split(" ", limit = 2)
        operation to arguments.split(" ")
    }

    override fun run(part2: Boolean): Any {
        return generateSequence(0) { it + 1 }.first {
            generateSignal(it).take(8).joinToString("") == "01010101"
        }
    }

    private fun generateSignal(input: Int) = sequence {
        var index = 0
        val registers = mutableMapOf("a" to input).withDefault { 0 }
        while (index in instructions.indices) {
            val (operation, arguments) = instructions[index]
            when (operation) {
                "cpy" -> {
                    registers[arguments[1]] = arguments[0].toIntOrNull() ?: registers.getValue(arguments[0])
                    index++
                }
                "inc" -> {
                    registers[arguments[0]] = registers.getValue(arguments[0]) + 1
                    index++
                }
                "dec" -> {
                    registers[arguments[0]] = registers.getValue(arguments[0]) - 1
                    index++
                }
                "jnz" -> index += if (arguments[0].toIntOrNull() ?: registers.getValue(arguments[0]) != 0) arguments[1].toInt() else 1
                "out" -> {
                    yield(arguments[0].toIntOrNull() ?: registers.getValue(arguments[0]))
                    index++
                }
            }
        }
    }
}

fun main() {
    println(Signal().run(part2 = false))
}