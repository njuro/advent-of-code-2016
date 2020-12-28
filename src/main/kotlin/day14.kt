import utils.readInputBlock
import java.security.MessageDigest

/** [https://adventofcode.com/2016/day/14] */
class Keycodes : AdventOfCodeTask {
    override fun run(part2: Boolean): Any {
        return generateKeys(readInputBlock("14.txt"), stretched = part2).drop(63).first()
    }

    private fun generateKeys(salt: String, stretched: Boolean) = sequence {
        var index = 0
        val cache = mutableMapOf<Int, String>()
        val pattern = Regex("([a-f0-9])\\1\\1")
        val times = if (stretched) 2017 else 1
        while (true) {
            val hash = cache.computeIfAbsent(index) { md5("$salt$it", times) }
            val match = pattern.find(hash)
            if (match != null) {
                val group = match.value.first().toString().repeat(5)
                if ((1..1000).any { group in cache.computeIfAbsent(index + it) { next -> md5("$salt$next", times) } }) {
                    yield(index)
                }
            }
            index++
        }
    }

    companion object {
        private val md5 = MessageDigest.getInstance("MD5")
    }

    private fun md5(str: String, times: Int): String {
        var input = str
        repeat(times) {
            input = md5.digest(input.toByteArray()).joinToString("") { "%02x".format(it) }
        }
        return input
    }
}

fun main() {
    println(Keycodes().run(part2 = true))
}