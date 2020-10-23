package crypto

fun ByteArray.toHex() = this.joinToString("") { "%02x".format(it) }