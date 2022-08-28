package folding

object L {
    inline operator fun <T> get(vararg a: T): List<T> = listOf(*a)
}

val <T> List<T>.head: T
    get() = this.first()

val <T> List<T>.tail: List<T>
    get() = this.subList(1, this.size)

/**
 * A fold right
 *
 * fold f v [ ] = v
 * fold f v (x : xs) = f x ( fold f v xs)
 */
fun <T, R> GOLD(
    f: (T, R) -> R,
    value: R,
    data: List<T>
): R {
    return when (data.isEmpty()) {
        true -> value
        false -> f(data.head, GOLD(f, value, data.tail))
    }
}

/**
 * A fold left
 *
 * foldl f v [ ] = v
 * foldl f v (x : xs) = foldl f (f v x) xs
 */
fun <T, R> GOLDL(
    f: (T, R) -> R,
    value: R,
    data: List<T>
): R {
    return when (data.isEmpty()) {
        true -> value
        false -> GOLDL(f, f(data.head, value), data.tail)
    }
}
