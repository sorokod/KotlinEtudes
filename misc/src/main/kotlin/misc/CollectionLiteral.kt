package language

object C {
    inline operator fun <T> get(vararg a: T) = listOf(*a)
}

inline operator fun <T> List<T>.get(range: IntRange) = slice(range)

fun main() {
    println(C[0, 1, 2])    // [0, 1, 2]
    println(C[0, 1, C[2]]) // [0, 1, [2]]


    val l = C[0, 1, 2, 3]
    println(l[0..2])      // [0,1,2]
    println(l[0 until 3]) // [0,1,2]


}