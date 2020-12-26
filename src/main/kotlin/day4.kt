import utils.readInputLines
import java.util.function.Function

/** [https://adventofcode.com/2016/day/4] */
class Rooms : AdventOfCodeTask {

    data class Room(val name: String, val id: Int, val checksum: String) {
        fun isValid(): Boolean {
            val counter = name.replace("-", "").associateWith { name.count { c -> c == it } }
            val comparator =
                Comparator.comparingInt<Char> { c -> counter[c]!! }.reversed().thenComparing(Function.identity())
            return counter.toSortedMap(comparator).keys.joinToString("").take(5) == checksum
        }
    }

    override fun run(part2: Boolean): Any {
        val pattern = Regex("(.+)-(\\d+)\\[(\\w+)]")
        val rooms = readInputLines("4.txt").map {
            val (name, id, checksum) = pattern.matchEntire(it)!!.destructured
            Room(name, id.toInt(), checksum)
        }.filter(Room::isValid)

        return if (part2) rooms.first {
            it.name.map { c ->
                if (c == '-') ' ' else {
                    val next = c + it.id % 26
                    if (next > 'z') 'a' + (next.toInt() % 'z'.toInt()) - 1 else next
                }
            }.joinToString("") == "northpole object storage"
        }.id else rooms.sumBy { it.id }
    }
}

fun main() {
    println(Rooms().run(part2 = true))
}