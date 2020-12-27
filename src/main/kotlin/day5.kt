import utils.readInputBlock
import java.security.MessageDigest

/** [https://adventofcode.com/2016/day/5] */
class Day5 : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        return sequence(readInputBlock("5.txt"), shuffled = part2).take(8)
            .sortedBy { it.first }.map { it.second }.joinToString("")
    }

    private fun sequence(id: String, shuffled: Boolean) = sequence {
        var index = 0
        val remaining = (0..7).toMutableSet()
        while (true) {
            val hash = md5(id + index)
            if (hash.startsWith("00000")) {
                if (shuffled) {
                    val position = Character.getNumericValue(hash[5])
                    if (position in remaining) {
                        remaining.remove(position)
                        yield(position to hash[6])
                    }
                } else {
                    yield(0 to hash[5])
                }
            }
            index++
        }
    }

    companion object {
        private val md5 = MessageDigest.getInstance("MD5")
    }

    private fun md5(str: String) = md5
        .digest(str.toByteArray()).joinToString("") { "%02x".format(it) }
}

fun main() {
    println(Day5().run(part2 = true))
}