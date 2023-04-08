package misc

import kotlin.math.hypot
import kotlin.math.pow
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Complex(real: Number, imaginary: Number) {
    private val re = real.toDouble()
    private val im = imaginary.toDouble()
    val scale = re.pow(2) + im.pow(2)


    fun abs(): Double = hypot(re, im)

    operator fun unaryMinus(): Complex = Complex(-re, -im)
    operator fun plus(other: Number): Complex = Complex(re + other.toDouble(), im)
    operator fun minus(other: Number): Complex = Complex(re - other.toDouble(), im)
    operator fun times(other: Number): Complex = Complex(re * other.toDouble(), im * other.toDouble())
    operator fun div(other: Number): Complex = Complex(re / other.toDouble(), im / other.toDouble())


    operator fun plus(other: Complex): Complex = Complex(re + other.re, im + other.im)
    operator fun minus(other: Complex): Complex = Complex(re - other.re, im - other.im)
    operator fun times(other: Complex): Complex = Complex(
        (re * other.re) - (im * other.im),
        (re * other.im) + (im * other.re)
    )
}

/**
 * Return false iff the orbit escapes
 */
fun mandelbrot(c: Complex, maxIterations: Int): Boolean {
    var z = c

    repeat(maxIterations) {
        if (z.scale >= 4) return false
        else z = z * z + c
    }
    return true // escapes
}


/**
 *
 */
@OptIn(ExperimentalTime::class)
fun main() {
    measureTimedValue {
        (-40..40).forEach { im ->
            (-50..50).forEach { re ->
                mandelbrot(c = Complex(re - 25, im) / 35, maxIterations = 255).also { stays ->
                    print(if (stays) '*' else ' ')
                }
            }
            println()
        }
    }.also {
        println("Done in: ${it.duration.inWholeMilliseconds} millis.")
    }
}