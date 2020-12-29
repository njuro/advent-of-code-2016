import utils.readInputBlock

/** [https://adventofcode.com/2016/day/19] */
class Day19 : AdventOfCodeTask {

    data class Elf(val id: Int) {
        lateinit var next: Elf
    }

    override fun run(part2: Boolean): Any {
        val size = readInputBlock("19.txt").toInt()
        val elves = (1..size).map { Elf(it) }.apply {
            zipWithNext().forEach { (first, second) -> first.next = second; }
        }
        elves.last().next = elves.first()
        var current = elves.first()
        while (current.next != current) {
            current.next = current.next.next
            current = current.next
        }
        return current.id
    }
}

fun main() {
    println(Day19().run(part2 = true))
}