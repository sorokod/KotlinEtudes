package rosettacode

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SubstitutionCipherTest : FunSpec({

    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz "
    val key = "VsciBjedgrzyHalvXZKtUPumGfIwJxqOCFRApnDhQWobLkESYMTN0"
    val cypher = SubstitutionCipher(alphabet, key)


    test("encode") {
        val expected = "dqnnQ0tFqbq"
        val actual = cypher.encode("Hello There")

        actual shouldBe expected
    }

    test("encode - decode") {
        val expected = "Hello There"

        cypher.encode(expected).also { encoded ->
            cypher.decode(encoded) shouldBe expected
        }
    }
})
