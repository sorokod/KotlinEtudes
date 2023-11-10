package language

/**
 * Enforces certain combinations of null / non-null arguments.
 *
 */
sealed interface ADate {
    val year: Int
    val month: Int?
    val day: Int?

    operator fun component1() = year
    operator fun component2() = month
    operator fun component3() = day

    companion object {
        operator fun invoke(year: Int): ADate = Impl(year, null, null)
        operator fun invoke(year: Int, month: Int): ADate = Impl(year, month, null)
        operator fun invoke(year: Int, month: Int, day: Int): ADate = Impl(year, month, day)
    }

    private data class Impl(override val year: Int, override val month: Int?, override val day: Int?) : ADate
}

fun main() {
    ADate(2023).also { println(it) }

    ADate(2023,11).also { println(it) }
    ADate(2023,11, 9).also { println(it) }
}