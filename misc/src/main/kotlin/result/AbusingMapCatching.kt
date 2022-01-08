package result

/**
 * Abusing Result.mapCatching() to implement a chain of transformations / validations
 *
 * The validation functions indicate failure by throwing an exception ( see [fail()] )
 */


/** A convenience thing **/
inline fun <reified T> fail(msg: String): T = throw IllegalArgumentException(msg)

/**  **/
fun validSize(requiredSize: Int, list: List<String>): List<String> =
    when (list.size <= requiredSize) {
        true -> list
        false -> fail("validSize - got ${list.size}")
    }

/** All elements can be converted to Ints **/
fun validData(list: List<String>): List<Int> =
    when (list.map { it.toIntOrNull() }.all { it != null }) {
        true -> list.map { it.toInt() }
        false -> fail("validData - got $list")
    }


/** **/
fun validSum(requiredSum: Int, list: List<Int>): Int =
    when (list.sum() <= requiredSum) {
        true -> list.sum()
        false -> fail("validSum - got ${list.sum()}")
    }

fun drive(data: List<String>) {
    Result.success(data)
        .mapCatching { validSize(3, it) }
        .mapCatching { validData(it) }
        .mapCatching { validSum(10, it) }
        .fold(
            onSuccess = { println("OK> $it") },
            onFailure = { println("FAILED> ${it.message}") }
        )
}

fun main() {
    drive(listOf("a", "b", "c", "d"))  // FAILED> validSize - got 4
    drive(listOf("a", "b", "c"))       // FAILED> validData - got [a, b, c]
    drive(listOf("100"))               // FAILED> validSum - got 100
    drive(listOf("101", "-1")) //      // FAILED> validSum - got 100
    drive(listOf("-1", "11", "-2"))    // OK> 8
}
