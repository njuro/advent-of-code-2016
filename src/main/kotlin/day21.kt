import utils.readInputLines

/** [https://adventofcode.com/2016/day/21] */
class Scrambler : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val input = if (part2) "fbgdceah" else "abcdefgh"
        
        return loadInstructions(
            readInputLines("21.txt"),
            reversed = part2
        ).fold(input.toList()) { password, instruction ->
            val tokens = instruction.split(" ")
            when ("${tokens[0]} ${tokens[1]}") {
                "swap position" -> {
                    val x = tokens[2].toInt()
                    val y = tokens[5].toInt()
                    password.mapIndexed { index, c ->
                        when (index) {
                            x -> password[y]
                            y -> password[x]
                            else -> c
                        }
                    }
                }
                "swap letter" -> {
                    val x = tokens[2].first()
                    val y = tokens[5].first()
                    password.map {
                        when (it) {
                            x -> y
                            y -> x
                            else -> it
                        }
                    }
                }
                "rotate left" -> {
                    val steps = tokens[2].toInt()
                    password.mapIndexed { index, _ -> password[(index + steps) % password.size] }
                }
                "rotate right" -> {
                    val steps = tokens[2].toInt()
                    password.mapIndexed { index, _ -> password[(index + (password.size - steps)) % password.size] }
                }
                "rotate based" -> {
                    val steps = password.indexOf(tokens[6].first()).let {
                        if (part2) {
                            password.size - (it / 2 + if (it % 2 == 1 || it == 0) 1 else 5)
                        } else {
                            it + if (it > 3) 2 else 1
                        }
                    }
                    password.mapIndexed { index, _ -> password[(index + (password.size - steps % password.size)) % password.size] }
                }
                "reverse positions" -> {
                    val x = tokens[2].toInt()
                    val y = tokens[4].toInt() + 1
                    password.subList(0, x) + password.subList(x, y).reversed() + password.subList(y, password.size)
                }
                "move position" -> {
                    val x = tokens[2].toInt()
                    val y = tokens[5].toInt()
                    password.toMutableList().apply {
                        add(y, removeAt(x))
                    }
                }
                else -> throw IllegalArgumentException()
            }
        }.joinToString("")
    }

    private fun loadInstructions(instructions: List<String>, reversed: Boolean) = if (reversed) instructions.map {
        when {
            it.startsWith("move position") -> {
                val tokens = it.split(" ")
                "move position ${tokens[5]} to position ${tokens[2]}"
            }
            "left" in it -> it.replace("left", "right")
            "right" in it -> it.replace("right", "left")
            else -> it
        }
    }.reversed() else instructions
}

fun main() {
    println(Scrambler().run(part2 = true))
}