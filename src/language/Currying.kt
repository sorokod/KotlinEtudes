package language

/**
 * Created by David Soroko on 23/11/2015.
 *
 * Currying example
 */

// Kotlin version of a currying example from "Scala By Example"
fun sigmaRecursive(f: (Int) -> Int): (Int, Int) -> Int {
    fun applyF(a: Int, b: Int): Int {
        return ( if (a > b) 0 else f(a) + applyF(a + 1, b) )
    }
    return ::applyF
}


fun sigma(f: (Int) -> Int): (IntRange) -> Int {
    fun applyF(range: IntRange): Int {
        var result = 0
        for (i in range) result += f(i)
        return result
    }
    return ::applyF
}

fun sigmaNoCurry(range: IntRange, f: (Int) -> Int): Int {
    var result = 0
    for (i in range) result += f(i)
    return result
}



fun lookUp(db: Map<String, String>): (String) -> String? {
    fun dbLookup(key: String): String? {
        return db.get(key)
    }
    return ::dbLookup
}

// lookUp is simple enough to collapse to:
fun lookUp2(map: Map<String, String>): (String) -> String? = { map.get(it) }




fun main(args: Array<String>) {

    val sigmaOfSum = sigma { x -> x }
    // These are all equivalent
    val sigmaOfProduct = sigma { x -> x * x }
    val sigmaOfProduct1 = sigma { x -> x * x }
    val sigmaOfProduct2 = sigma { it * it }

    val range = 1..5

    println(sigmaOfSum(range))
    println(sigmaOfProduct(range))
    println(sigmaOfProduct1(range))
    println(sigmaOfProduct2(range))


    val customerLookup = lookUp(mapOf("1" to "Customer1", "2" to "Customer2"))
    val productLookup = lookUp(mapOf("1" to "Product1", "2" to "Product2"))
    val productLookup2 = lookUp(mapOf("1" to "Product1", "2" to "Product2"))

    println(customerLookup("1")) // Customer1
    println(productLookup("1")) // Product1
    println(productLookup2("1")) // Product1

}
