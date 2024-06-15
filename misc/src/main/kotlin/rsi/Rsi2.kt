package rsi

import kotlin.math.abs

/**
 * An implementation of the Relative Strength Index (RSI).
 *
 * See https://en.wikipedia.org/wiki/Relative_strength_index
 *
 */


/**
 * Simulates time series data.
 */
data class Data(
    val open: Double,
    val close: Double,
    val delta: Double = close - open
)

/**
 * The RSI values for a specific time series data point.
 */
data class RSI(
    var avgGain: Double = 0.0,
    var avgLoss: Double = 0.0,
    var rs: Double = 0.0,
    var rsi: Double = 0.0
)


/**
 *
 */
fun rsi(data: Array<Data>, lookBack: Int): List<RSI> {

    check(data.size > lookBack) { "time series size must be > ${lookBack - 1}" }

    /**
     *
     */
    fun rsi_i(data: Array<Data>, index: Int, lookBack: Int, rsiData: List<RSI>) {

        check(index >= lookBack - 1) { "index must be >= ${lookBack - 1}" }

        if (index == lookBack - 1) {
            val lookBackSlice = data.take(lookBack)

            with(rsiData[index]) {
                avgGain = lookBackSlice.filter { it.delta > 0 }.sumOf { it.delta } / lookBack.toDouble()
                avgLoss = abs(lookBackSlice.filter { it.delta < 0 }.sumOf { it.delta } / lookBack.toDouble())
            }

        } else {
            val delta = data[index].delta

            val gain = if (delta < 0) 0.0 else delta
            val loss = if (delta > 0) 0.0 else abs(delta)

            with(rsiData[index]) {
                avgGain = (rsiData[index - 1].avgGain * (lookBack - 1) + gain) / lookBack
                avgLoss = (rsiData[index - 1].avgLoss * (lookBack - 1) + loss) / lookBack

                // when avgLoss == 0 rs is set to max value to simulate infinity
                rs = if (avgLoss != 0.0) avgGain / avgLoss else Double.MAX_VALUE
                rsi = 100.0 - 100.0 / (1.0 + rs)
            }
        }
    }

    val rsiData: List<RSI> = List(data.size) { RSI() }

    for (i in (lookBack - 1)..data.lastIndex) {
        rsi_i(data, i, lookBack, rsiData)
    }

    return rsiData
}


fun main() {
    val data = arrayOf(
        Data(1.0, 0.0),
        Data(0.0, 3.0),
        Data(0.0, 2.0),
        Data(1.0, 0.0),
        Data(2.0, 0.0),
        Data(2.0, 0.0),
        Data(0.0, 1.0),
        Data(2.0, 0.0),
        Data(0.0, 2.0),
        Data(0.0, 2.0),
        Data(0.0, 4.0),
        Data(0.0, 2.0),
        Data(0.0, 1.0),
        Data(0.0, 1.0),
        Data(0.0, 1.0),
        Data(3.0, 0.0),
        Data(0.0, 1.0),
        Data(1.0, 0.0),
        Data(0.0, 2.0),
        Data(0.0, 4.0),
        Data(0.0, 2.0),
        Data(0.0, 2.0),
        Data(0.0, 1.0),
        Data(2.0, 0.0),
        Data(0.0, 3.0),
        Data(2.0, 0.0),
        Data(2.0, 0.0),
        Data(0.0, 4.0),
    )

    val rsiList = rsi(data, lookBack = 14)

    // markdown table
    println("""
        | i  | SMMA UP              | SMMA DOWN            | RS      | RSI    |
        |:---|:---------------------|:---------------------|:--------|:-------|
    """.trimIndent())
    rsiList.forEachIndexed() { i, row ->
        println("| `$i` | `${row.avgGain}`  | `${row.avgLoss}` | `${row.rs}`  | `${row.rsi}` |")
    }
}

/*
SMMA UP

14  1.2857142857142858
15  1.2653061224489797
16  1.174927113702624
17  1.1624323198667224
18  1.0794014398762422
19  1.145158479885082
20  1.349075731321862
21  1.395570321941729
22  1.4387438703744626
23  1.4074050224905723
24  1.3068760923126743
25  1.4278135142903405
26  1.3258268346981734
27  1.2311249179340182
28  1.42890170951016
=================

SMMA DOWN

14  0.5714285714285714
15  0.5306122448979591
16  0.7069970845481048
17  0.6564972927946687
18  0.6810332004521923
19  0.6323879718484643
20  0.5872174024307169
21  0.5452733022570943
22  0.5063252092387304
23  0.4701591228645353
24  0.5794334712313542
25  0.5380453661434004
26  0.6424706971331575
27  0.7394370759093605
28  0.6866201419158348
 */