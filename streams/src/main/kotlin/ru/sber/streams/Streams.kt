package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long = list
    .asSequence()
    .withIndex()
    .filter { it.index % 3 == 0 }
    .map { it.value }
    .sum()

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> = generateSequence(Pair(0, 1)) {
    Pair(it.second, it.first + it.second)
}
    .map { it.first }


// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = this
    .customers
    .asSequence()
    .map { it.city }
    .distinct()
    .toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = this
    .customers.asSequence().map { it.orders.asSequence() }
    .flatMap { it.flatMap { it.products.asSequence()
            .distinct() }
        .distinct() }
    .distinct()
    .toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = this
    .customers.asSequence().maxByOrNull{ it.orders.size }

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = this.orders.asSequence()
    .map { it.products.asSequence() }
    .flatMap { it }
    .maxByOrNull { it.price }


// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> = this
    .customers.asSequence()
    .map { Pair(it.city, it.orders.asSequence().map { if (it.isDelivered) it.products.size else 0 }.sum()) }
    .groupBy {it.first}
    .asSequence()
    .map { Pair(it.key, it.value.asSequence().sumOf { it.second }) }
    .toMap()
//    .groupingBy { it.city }.aggregate { key, accumulator, element, first ->
//        if (first) {
//            0
//
//        } else {
//            accumulator?.plus(element.orders.asSequence().map { if (it.isDelivered) it.products.size else 0 }.sum()) ?: 0
//        }
//    }

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> = this
    .customers.asSequence().map { Pair(it.city, it.orders.asSequence().map { it.products.asSequence() }.flatMap { it }) }
    .groupBy {it.first}
    .asSequence()
    .map { Pair(it.key, it.value.asSequence().flatMap { it.second }.groupingBy { it }.eachCount().maxByOrNull { it.value }!!.key) }
    .toMap()

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> = emptySet()

