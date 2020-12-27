import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AdventOfCodeTasksTest {

    @Test
    fun day1() {
        runTaskTest(Navigation(), 239, 141)
    }

    @Test
    fun day2() {
        runTaskTest(Code(), "38961", "46C92")
    }

    @Test
    fun day3() {
        runTaskTest(Triangles(), 862, 1577)
    }

    @Test
    fun day4() {
        runTaskTest(Rooms(), 185371, 984)
    }

    @Test
    fun day5() {
        runTaskTest(Hashes(), "4543c154", "1050cbbd")
    }

    @Test
    fun day6() {
        runTaskTest(Message(), "umejzgdw", "aovueakv")
    }

    @Test
    fun day7() {
        runTaskTest(Addresses(), 110, 242)
    }

    private fun runTaskTest(task: AdventOfCodeTask, part1Result: Any, part2Result: Any) {
        assertEquals(part1Result, task.run())
        assertEquals(part2Result, task.run(part2 = true))
    }
}