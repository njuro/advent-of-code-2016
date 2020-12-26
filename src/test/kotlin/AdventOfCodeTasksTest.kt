import org.junit.jupiter.api.Assertions.assertEquals

class AdventOfCodeTasksTest {

    // @Test
    // fun day1() {
    //     runTaskTest(Floors(), 138, 1771)
    // }
    //

    private fun runTaskTest(task: AdventOfCodeTask, part1Result: Any, part2Result: Any) {
        assertEquals(part1Result, task.run())
        assertEquals(part2Result, task.run(part2 = true))
    }
}