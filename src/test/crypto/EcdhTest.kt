package crypto

import org.junit.Assert.*
import org.junit.Test

class EcdhTest {

    @Test
    fun `should generated same key every time`() {
        val ecdh1 = Ecdh()
        val ecdh2 = Ecdh()

        val keySet = (1..100).map { ecdh1.gen_SharedSecret(ecdh2.getPublic()).toHex() }.toSet()

        assertEquals(1, keySet.size)
    }


    @Test
    fun `should agree on the generated shared key`() {
        val ecdh1 = Ecdh()
        val ecdh2 = Ecdh()

        val key2 = ecdh2.gen_SharedSecret(ecdh1.getPublic())
        val key1 = ecdh1.gen_SharedSecret(ecdh2.getPublic())

        assertEquals(key1.toHex(), key2.toHex())
    }
}