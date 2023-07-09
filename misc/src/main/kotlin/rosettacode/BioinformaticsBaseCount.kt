package rosettacode

/**
 * https://rosettacode.org/wiki/Bioinformatics/base_count#Kotlin
 */

const val BASE_SEQUENCE =
    "CGTAAAAAATTACAACGTCCTTTGGCTATCTCTTAAACTCCTGCTAAATGCTCGTGCTTTCCAATTATGTAAGCGTTCCGAGACGGGGTGGTCGATTCTGAGGACAAAGGTCAAGATGGAGCGCATCGAACGCAATAAGGATCATTTGATGGGACGTTTCGTCGACAAAGTCTTGTTTCGAGAGTAACGGCTACCGTCTTCGATTCTGCTTATAACACTATGTTCTTATGAAATGGATGTTCTGAGTTGGTCAGTCCCAATGTGCGGGGTTTCTTTTAGTACGTCGGGAGTGGTATTATATTTAATTTTTCTATATAGCGATCTGTATTTAAGCAATTCATTTAGGTTATCGCCGCGATGCTCGGTTCGGACCGCCAAGCATCTGGCTCCACTGCTAGTGTCCTAAATTTGAATGGCAAACACAAATAAGATTTAGCAATTCGTGTAGACGACCGGGGACTTGCATGATGGGAGCAGCTTTGTTAAACTACGAACGTAAT"


fun doSequence(sequence: String, width: Int = 50) {

    fun printLabeled(label: Int, data: String) =
        label.toString().padStart(5).also { println("$it: $data") }


    println("SEQUENCE:")
    sequence.chunked(width).forEachIndexed { i, chunk ->
        printLabeled(i * width + chunk.length, chunk)
    }

    doStats(sequence)
}


fun doStats(sequence: String) {
    sequence.groupingBy { it }.eachCount()
        .let { count ->
            """
                COUNT:
                    A: ${count['A']} 
                    C: ${count['C']}
                    G: ${count['G']}
                    T: ${count['T']}
                TOTAL: ${sequence.length}    
            """.trimIndent()
        }.also { println(it) }
}

fun main() = doSequence(BASE_SEQUENCE)
