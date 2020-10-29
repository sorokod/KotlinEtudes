import language.functional.quickSort
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.random.Random

class QuicksortTest {

    @Test
    fun `empty list sorts to empty list`() {
        val list: List<Int> = emptyList()

        list.quickSort().also {
            assertTrue(it.isEmpty())
        }
    }

    @Test
    fun `random list sorts to ascending`() {
        val list: List<Int> = (1..10).map { Random.nextInt() }

        list.quickSort().also {
            assertEquals(list.sorted(), it)
        }
    }
}
