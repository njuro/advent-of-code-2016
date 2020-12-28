import utils.readInputLines

/** [https://adventofcode.com/2016/day/12] */
class Assembly : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val instructions = readInputLines("12.txt").map {
            val (operation, arguments) = it.split(" ", limit = 2)
            operation to arguments.split(" ")
        }
        
        var index = 0
        val registers = mutableMapOf("c" to if (part2) 1 else 0).withDefault { 0 }
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
            }
        }

        return registers.getValue("a")
    }
}

fun main() {
    println(Assembly().run(part2 = true))
}