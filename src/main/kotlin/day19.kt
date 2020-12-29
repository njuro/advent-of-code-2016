import utils.readInputBlock

/** [https://adventofcode.com/2016/day/19] */
class Presents : AdventOfCodeTask {

    data class Elf(val id: Int) {
        lateinit var next: Elf
        lateinit var prev: Elf
    }

    override fun run(part2: Boolean): Any {
        val size = readInputBlock("19.txt").toInt()
        val elves = (1..size).map { Elf(it) }.apply {
            zipWithNext().forEach { (first, second) -> first.next = second; second.prev = first }
        }
        elves.last().next = elves.first()
        elves.first().prev = elves.last()

        var current = elves.first()
        var opposite = elves[size / 2]
        var remaining = size
        while (remaining > 1) {
            if (part2) {
                opposite.prev.next = opposite.next
                opposite.next.prev = opposite.prev
                current = current.next
                opposite = if (remaining % 2 == 0) opposite.next else opposite.next.next
            } else {
                current.next = current.next.next
                current = current.next
            }
            remaining--
        }
        
        return current.id
    }
}

fun main() {
    println(Presents().run(part2 = true))
}