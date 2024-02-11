package rosettacode

class SubstitutionCipher( alphabet: String,  key: String) {
    private val dic = alphabet.zip(key)

    fun encode(str: String) =
        str.map { ch -> dic.first { it.first == ch }.second }.stringify()

    fun decode(str: String) =
        str.map { ch -> dic.first { it.second == ch }.first }.stringify()

    private fun <T> Iterable<T>.stringify() =
        this.joinToString(separator = "")
}
