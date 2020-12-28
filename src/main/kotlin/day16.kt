import utils.readInputBlock

/** [https://adventofcode.com/2016/day/16] */
class Curve : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        val diskSize = if (part2) 35651584 else 272
        return generateSequence(readInputBlock("16.txt")) { it.process() }
            .first { it.length >= diskSize }
            .take(diskSize)
            .checksum()
    }

    private fun String.process() = this + "0" + reversed().map { 1 - Character.getNumericValue(it) }.joinToString("")
    private fun String.checksum(): String =
        windowed(2, 2, false).map { if (it.first() == it.last()) 1 else 0 }.joinToString("").let {
            if (it.length % 2 == 1) it else it.checksum()
        }
}

fun main() {
    println(Curve().run(part2 = false))
}