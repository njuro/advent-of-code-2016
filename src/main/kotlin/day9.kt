import utils.readInputBlock

/** [https://adventofcode.com/2016/day/9] */
class Decompression : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        return getDecompressedLength(readInputBlock("9.txt").trim(), recursive = part2)
    }

    private fun getDecompressedLength(input: String, times: Int = 1, recursive: Boolean): Long {
        val pattern = Regex("(\\d+)x(\\d+)")
        var length = 0L
        var index = 0
        while (index < input.length) {
            val current = input[index]
            if (current == '(') {
                val closing = input.indexOf(')', startIndex = index)
                val (subLength, subTimes) = pattern.matchEntire(input.substring(index + 1, closing))!!.destructured
                val subInput = input.substring(closing + 1, closing + 1 + subLength.toInt())
                length += if (recursive)
                    getDecompressedLength(
                        subInput, subTimes.toInt(), recursive
                    ) else subInput.length * subTimes.toLong()
                index = closing + subLength.toInt() + 1
            } else {
                length++
                index++
            }
        }

        return length * times
    }
}

fun main() {
    println(Decompression().run(part2 = false))
}