package language

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
fun main() {
    val uuid1 = Uuid.random().also {
        println(it.toString())
        println(it.toHexString())
    }

    check(Uuid.parse(uuid1.toString()) == uuid1)
    check(Uuid.parseHex(uuid1.toHexString()) == uuid1)

}