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

    @Test
    fun day8() {
        runTaskTest(
            Lights(), 128, """
            ####..##...##..###...##..###..#..#.#...#.##...##..
            #....#..#.#..#.#..#.#..#.#..#.#..#.#...##..#.#..#.
            ###..#..#.#..#.#..#.#....#..#.####..#.#.#..#.#..#.
            #....#..#.####.###..#.##.###..#..#...#..####.#..#.
            #....#..#.#..#.#.#..#..#.#....#..#...#..#..#.#..#.
            ####..##..#..#.#..#..###.#....#..#...#..#..#..##..
        """.trimIndent()
        )
    }

    @Test
    fun day9() {
        runTaskTest(Decompression(), 97714L, 10762972461L)
    }

    @Test
    fun day10() {
        runTaskTest(Microchips(), 93, 47101)
    }

    @Test
    fun day11() {
        runTaskTest(Elevator(), 31, 55)
    }

    @Test
    fun day12() {
        runTaskTest(Assembly(), 318020, 9227674)
    }

    @Test
    fun day13() {
        runTaskTest(Maze(), 86, 127)
    }

    @Test
    fun day14() {
        runTaskTest(Keycodes(), 35186, 22429)
    }

    @Test
    fun day15() {
        runTaskTest(Discs(), 317371, 2080951)
    }

    @Test
    fun day16() {
        runTaskTest(Curve(), "10010100110011100", "01100100101101100")
    }

    @Test
    fun day17() {
        runTaskTest(Vault(), "DUDDRLRRRD", 578)
    }

    @Test
    fun day18() {
        runTaskTest(Traps(), 2005, 20008491)
    }

    @Test
    fun day19() {
        runTaskTest(Presents(), 1841611, 1423634)
    }

    @Test
    fun day20() {
        runTaskTest(Firewall(), 23923783L, 125)
    }

    @Test
    fun day21() {
        runTaskTest(Scrambler(), "gcedfahb", "hegbdcfa")
    }

    private fun runTaskTest(task: AdventOfCodeTask, part1Result: Any, part2Result: Any) {
        assertEquals(part1Result, task.run())
        assertEquals(part2Result, task.run(part2 = true))
    }
}