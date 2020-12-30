import utils.readInputLines

/** [https://adventofcode.com/2016/day/23] */
class ToggleAssembly : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val instructions = readInputLines("23.txt").map {
            val (operation, arguments) = it.split(" ", limit = 2)
            operation to arguments.split(" ")
        }.toMutableList()

        if (part2) {
            // solved on paper
            return (1L..12L).reduce { a, b -> a * b } +
                setOf(19, 20).fold(1L) { acc, next -> acc * instructions[next].second.first().toInt() }
        }

        var index = 0
        val registers = mutableMapOf("a" to 7).withDefault { 0 }
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
                "jnz" -> {
                    val offset = arguments[1].toIntOrNull() ?: registers.getValue(arguments[1])
                    index += if (arguments[0].toIntOrNull() ?: registers.getValue(arguments[0]) != 0) offset else 1
                }
                "tgl" -> {
                    val otherIndex = index + registers.getValue(arguments[0])
                    instructions.getOrNull(otherIndex)?.also {
                        val newOperation = when (it.first) {
                            "inc" -> "dec"
                            "dec" -> "inc"
                            "tgl" -> "inc"
                            "jnz" -> "cpy"
                            "cpy" -> "jnz"
                            else -> throw IllegalArgumentException()
                        }
                        instructions.removeAt(otherIndex)
                        instructions.add(otherIndex, newOperation to it.second)
                    }
                    index++
                }
            }
        }

        return registers.getValue("a")
    }
}

fun main() {
    println(ToggleAssembly().run(part2 = true))
}