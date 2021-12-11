package crypto

import java.security.*
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.KeyAgreement

/**
 * Generate a shared secret key between two parties using ECDH
 *
 * Following https://neilmadden.blog/2016/05/20/ephemeral-elliptic-curve-diffie-hellman-key-agreement-in-java/
 */
class Ecdh {

    private val keyPair: KeyPair = gen_ECDH_keyPair()

    fun getPublic(): ByteArray = keyPair.public.encoded // DER encoded

    /**
     * Generate public/private key pair
     *
     * By setting the key size to 256-bits, Java will select the NIST P-256 curve parameters (secp256r1).
     * For other key sizes, it will choose other NIST standard curves, e.g. P-384, P-521.
     * If you wish to use different parameters, then you must specify them explicitly using the
     * ECGenParameterSpec argument.
     *
     * See also: https://docs.oracle.com/en/java/javase/13/security/oracle-providers.html#GUID-091BF58C-82AB-4C9C-850F-1660824D5254
     */
    private inline fun gen_ECDH_keyPair(): KeyPair =
            KeyPairGenerator.getInstance("EC")
                    .also { kpGen -> kpGen.initialize(256) }
                    .let { kpGen -> kpGen.generateKeyPair() }


    /**
     * We assume that the other party is also using a NIST P-256 curve public key. We also assume that the output of
     * PublicKey.getEncoded() is an X.509-encoded key. This turns out to be true in the Oracle JRE, but I cannot find
     * any documented guarantee of this behaviour. A more robust approach would be to communicate the ECPoint and
     * ECParameterSpec of the public key, and use an ECPublicKeySpec to reconstruct the key, but that is even more work.
     */
    private fun decodeOtherPubKey(otherPK: ByteArray): PublicKey {
        val keySpec = X509EncodedKeySpec(otherPK)
        return KeyFactory.getInstance("EC").let {
            it.generatePublic(keySpec)
        }
    }

    fun gen_SharedSecret(otherPK: ByteArray): ByteArray {
        val otherPublicKey = decodeOtherPubKey(otherPK)

        val sharedSecret: ByteArray = with(KeyAgreement.getInstance("ECDH")) {
            init(keyPair.private)
            doPhase(otherPublicKey, true)
            generateSecret()
        }

        return deriveKey(sharedSecret, otherPublicKey.encoded, keyPair.public.encoded)
    }

    /**
     * We adopt the approach described in the libsodium documentation, of deriving a key by hashing the shared secret
     * and both public keys, but using SHA-256 rather than BLAKE2. These choices of algorithms and curves are purely for
     * convenience because they are readily available on the JVM without 3rd party libraries (e.g. Bouncy Castle).
     * The only trickiness is to ensure that we feed the public keys into the hash in the same order on both sides of
     * the agreement protocol. For simplicity, we do this by just sorting them lexicographically.
     *
     * A more sophisticated key derivation function, such as HKDF, can be used to derive further keys (for instance,
     * separate keys sending data in each direction, which is recommended).
     */
    private inline fun deriveKey(sharedSecret: ByteArray, pubKey1: ByteArray, pubKey2: ByteArray): ByteArray {
        val sortedPubKeys = listOf(pubKey1, pubKey2).sortedWith { a, b -> Arrays.compare(a, b) }
        return with(MessageDigest.getInstance("SHA-256")) {
            update(sharedSecret)
            update(sortedPubKeys[0])
            update(sortedPubKeys[1])

            digest()
        }
    }

}
