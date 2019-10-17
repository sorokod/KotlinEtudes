import junit.framework.Assert.assertEquals
import language.lookUp
import language.sigma
import org.junit.Test

class CurryingTest {

    private val range = 1..5

    @Test
    fun `sigma of +`() {
        val f = sigma { x -> x }
        val expected = range.sum()

        f(range).also {
            assertEquals(expected, it)
        }
    }

    @Test
    fun `sigma of x^2`() {
        val f = sigma { x -> x * x }
        val expected = range.map { it * it }.sum()

        f(range).also {
            assertEquals(expected, it)
        }
    }

    @Test
    fun `Db lookup`() {
        val lookup = lookUp((1..5).map { "$it" to "Customer$it" }.toMap())

        lookup("2").also {
            assertEquals("Customer2", it)
        }
    }
}