import utils.readInputLines

/** [https://adventofcode.com/2016/day/6] */
class Message : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val messages = readInputLines("6.txt")
        return (messages.first().indices).map { index ->
            messages.map { it[index] }.groupingBy { it }.eachCount().run {
                if (part2) minByOrNull { it.value } else maxByOrNull { it.value }
            }!!.key
        }.joinToString("")
    }
}

fun main() {
    println(Message().run(part2 = false))
}