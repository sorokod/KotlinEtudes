class Complex(real: Number, imaginary: Number) {
    val re = real.toDouble()
    val im = imaginary.toDouble()
    val scale = (re * re) + (im * im)

    companion object {
        val ZERO = Complex(0, 0)
        val ONE = Complex(1, 0)
        val I = Complex(0, 1)
    }

    fun reciprocal(): Complex {
        return Complex(re / scale, -im / scale)
    }

    fun abs(): Double = Math.hypot(re, im)
    fun abs2(): Double = scale

    operator fun unaryMinus(): Complex = Complex(-re, -im)
    operator fun plus(other: Number): Complex = Complex(re + other.toDouble(), im)
    operator fun minus(other: Number): Complex = Complex(re - other.toDouble(), im)
    operator fun times(other: Number): Complex = Complex(re * other.toDouble(), im * other.toDouble())
    operator fun div(other: Number): Complex = Complex(re / other.toDouble(), im / other.toDouble())


    operator fun plus(other: Complex): Complex = Complex(re + other.re, im + other.im)
    operator fun minus(other: Complex): Complex = Complex(re - other.re, im - other.im)
    operator fun times(other: Complex): Complex = Complex(
            (re * other.re) - (im * other.im),
            (re * other.im) + (im * other.re))

    operator fun div(other: Complex): Complex = this * other.reciprocal()
}

/**
 *
 */
fun mandelbrot(c: Complex, maxIterations: Int): Int? {
    var z = c
    (0..maxIterations).forEach { i ->
        if (z.abs2() >= 4) return i
        else z = z * z + c
    }
    return null
}


/**
 *
 */
fun main(args: Array<String>) {
    (-40..40).forEach { im ->
        (-50..50).forEach { re ->
            val m: Int? = mandelbrot(c = Complex(re - 25, im) / 35, maxIterations = 255)
            print(m?.let { ' ' } ?: '*')
        }
        println()
    }

}