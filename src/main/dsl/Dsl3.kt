package dsl3

import java.text.SimpleDateFormat
import java.util.*

data class Person(val name: String,
                  val dateOfBirth: Date,
                  val addresses: List<Address>)

data class Address(val street: String,
                   val number: Int,
                   val city: String)

// Same as dsl2 but we allow a list of addresses

fun person(block: PersonBuilder.() -> Unit): Person = PersonBuilder().apply(block).build()


class PersonBuilder {
    var name: String = ""

    private var dob: Date = Date()

    var dateOfBirth: String = ""
        set(value) {
            dob = SimpleDateFormat("yyyy-MM-dd").parse(value)
        }

    private val addresses = mutableListOf<Address>()

    fun address(block: AddressBuilder.() -> Unit) {
        addresses.add(AddressBuilder().apply(block).build())
    }

    fun build(): Person = Person(name, dob, addresses)
}

class AddressBuilder {
    var street: String = ""
    var number: Int = 0
    var city: String = ""

    fun build(): Address = Address(street, number, city)
}

fun main() {
    val person = person {
        name = "John"
        dateOfBirth = "1980-12-01"
        address {
            street = "Main Street"
            number = 12
            city = "London"
        }
        address {
            street = "Dev Avenue"
            number = 42
            city = "Paris"
        }
    }
    println(person)
}