package language

import java.util.*

// ###############################################
// https://kotlinlang.org/docs/inline-classes.html
// see tests
// ###############################################


interface ID

@JvmInline
value class XId(private val value: UUID) : ID {
    companion object {
        fun rand(): XId = XId(UUID.randomUUID())
        fun from(str: String): XId = XId(UUID.nameUUIDFromBytes(str.toByteArray()))
    }
}

@JvmInline
value class YId(private val value: UUID) : ID {
    companion object {
        fun rand(): YId = YId(UUID.randomUUID())
        fun from(str: String): YId = YId(UUID.nameUUIDFromBytes(str.toByteArray()))
    }
}
