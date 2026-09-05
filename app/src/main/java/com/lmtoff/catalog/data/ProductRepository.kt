package com.lmtoff.catalog.data

/**
 * Katalog verisinin kaynağı. UI katmanı yalnızca bu arayüzü bilir; veri şu an
 * uygulama içinde sabit tutuluyor, ileride uzak bir API ile değiştirilebilir.
 */
interface ProductRepository {
    fun allCategories(): List<String>

    fun allProducts(): List<Product>

    fun productsInCategory(category: String): List<Product>

    fun productById(id: Int): Product?
}

class InMemoryProductRepository(
    private val products: List<Product> = sampleProducts,
    private val categories: List<String> = productCategories
) : ProductRepository {

    override fun allCategories(): List<String> = categories

    override fun allProducts(): List<Product> = products

    override fun productsInCategory(category: String): List<Product> =
        if (category == ALL_CATEGORY) products else products.filter { it.category == category }

    override fun productById(id: Int): Product? = products.firstOrNull { it.id == id }

    companion object {
        const val ALL_CATEGORY = "Tümü"
    }
}
