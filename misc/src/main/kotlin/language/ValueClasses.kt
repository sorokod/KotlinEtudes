package language

import java.util.UUID

// ###############################################
// https://kotlinlang.org/docs/inline-classes.html
// ###############################################

@JvmInline
value class UserId(private val value: UUID)

@JvmInline
value class CustomerId(private val value: UUID) {

    companion object {
        fun rand(): CustomerId = CustomerId(UUID.randomUUID())
        fun from(str: String): CustomerId = CustomerId(UUID.nameUUIDFromBytes(str.toByteArray()))
    }

    fun versionAndVariant() = Pair(value.version(), value.variant())
}
