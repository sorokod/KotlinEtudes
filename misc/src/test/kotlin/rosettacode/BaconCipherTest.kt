package rosettacode

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe




class BaconCipherTest : StringSpec({

    "test: 'a b c' " {
        val bt = BaconTriple(
            "a b c",
            "As a second illustration of th",
            "as a seCONd illusTRATion of Th"
        )
        val (plainText, coverText, stegoText, decodedText) = bt

        BaconCipher.encode(plainText, coverText) shouldBe stegoText
        BaconCipher.decode(stegoText) shouldBe decodedText
    }

    "test: 'a b c' and coverText contains digit" {
        val bt = BaconTriple(
            "a b c",
            "8s a second illustration of th",
            "8s a secOND illustRATIon of tH",
            decodedText = "a b "
        )
        val (plainText, coverText, stegoText, decodedText) = bt

        BaconCipher.encode(plainText, coverText) shouldBe stegoText
        BaconCipher.decode(stegoText) shouldBe decodedText
    }
})

