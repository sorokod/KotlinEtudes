package dsl2

import java.text.SimpleDateFormat
import java.util.*

data class Person(val name: String,
                  val dateOfBirth: Date,
                  var address: Address?)

data class Address(val street: String,
                   val number: Int,
                   val city: String)

// because we switched from var to val we need the bulder classes:

fun person(block: PersonBuilder.() -> Unit): Person = PersonBuilder().apply(block).build()


class PersonBuilder {

    var name: String = ""

    private var dob: Date = Date()

    var dateOfBirth: String = ""
        set(value) {
            dob = SimpleDateFormat("yyyy-MM-dd").parse(value)
        }

    private var address: Address? = null

    fun address(block: AddressBuilder.() -> Unit) {
        address = AddressBuilder().apply(block).build()
    }

    fun build(): Person = Person(name, dob, address)

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
    }
    println(person)
}