package rosettacode


fun <T> Array<T>.rotate(n: Int): Array<T> {
    if (size < 2 || n % size == 0) return this

    val m = when (n > 0) {
        true -> n % size
        false -> ((n % size) + size) % size
    }
    return sliceArray(m until size) + sliceArray(0 until m)
}
