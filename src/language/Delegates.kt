    package language

/**
 * Created by David Soroko on 20/11/2015.
 *
 * Class delegation example
 */


class Customer()
class Product()

interface CustomerFinder {
    fun findCustomers(query: String): Set<Customer>
}

interface ProductFinder {
    fun findProducts(query: String): Set<Product>
}


class MegaFinder(customerFinder: CustomerFinder, productFinder: ProductFinder)
: CustomerFinder by customerFinder, ProductFinder by productFinder {

    fun loadAll() {
        val customers = findCustomers("*:*")
        val products = findProducts("*:*")
        //...
    }
}

