package language

/**
 * Created by David Soroko on 23/11/2015.
 *
 * Currying example
 */

// Kotlin version of a currying example from "Scala By Example"
fun sigmaRecursive(f: (Int) -> Int): (Int, Int) -> Int {
    fun applyF(a: Int, b: Int): Int {
        return (if (a > b) 0 else f(a) + applyF(a + 1, b))
    }
    return ::applyF
}


fun sigma(f: (Int) -> Int): (IntRange) -> Int =
        { range: IntRange ->
            var result = 0
            for (i in range) result += f(i)
            result
        }


fun lookUp(db: Map<String, String>): (String) -> String? {
    fun dbLookup(key: String): String? {
        return db[key]
    }
    return ::dbLookup
}

// lookUp is simple enough to collapse to:
fun lookUp2(map: Map<String, String>): (String) -> String? = { map.get(it) }
