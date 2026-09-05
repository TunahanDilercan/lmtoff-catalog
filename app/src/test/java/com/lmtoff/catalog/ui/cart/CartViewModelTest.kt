package com.lmtoff.catalog.ui.cart

import com.lmtoff.catalog.data.Product
import com.lmtoff.catalog.data.ProductColorOption
import com.lmtoff.catalog.data.ProductRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CartViewModelTest {

    private val towel = Product(
        id = 1,
        name = "Spor Havlusu",
        category = "Spor Havlusu",
        price = "449 TL",
        imageRes = 0,
        description = "test",
        colorOptions = listOf(
            ProductColorOption(label = "Pembe", swatch = 0L, imageRes = 0),
            ProductColorOption(label = "Siyah", swatch = 0L, imageRes = 0)
        )
    )

    private val shaker = Product(
        id = 2,
        name = "Shaker",
        category = "Shaker",
        price = "699 TL",
        imageRes = 0,
        description = "test"
    )

    private val repository = object : ProductRepository {
        private val products = listOf(towel, shaker)
        override fun allCategories() = listOf("Tümü")
        override fun allProducts() = products
        override fun productsInCategory(category: String) = products
        override fun productById(id: Int) = products.firstOrNull { it.id == id }
    }

    private fun viewModel() = CartViewModel(repository)

    @Test
    fun `sepet baslangicta bostur`() {
        val state = viewModel().state.value

        assertTrue(state.isEmpty)
        assertEquals(0, state.itemCount)
        assertEquals(0, state.total)
    }

    @Test
    fun `ayni urun tekrar eklendiginde adet artar`() {
        val viewModel = viewModel()

        viewModel.add(towel, towel.colorOptions.first())
        viewModel.add(towel, towel.colorOptions.first())

        val state = viewModel.state.value
        assertEquals(1, state.lines.size)
        assertEquals(2, state.lines.first().quantity)
        assertEquals(898, state.total)
    }

    @Test
    fun `ayni urunun farkli rengi ayri satir olur`() {
        val viewModel = viewModel()

        viewModel.add(towel, towel.colorOptions[0])
        viewModel.add(towel, towel.colorOptions[1])

        val state = viewModel.state.value
        assertEquals(2, state.lines.size)
        assertEquals(2, state.itemCount)
        assertEquals(898, state.total)
    }

    @Test
    fun `adet sifira dusurulunce satir silinir`() {
        val viewModel = viewModel()
        viewModel.add(shaker, null)

        viewModel.updateQuantity(viewModel.state.value.lines.first(), 0)

        assertTrue(viewModel.state.value.isEmpty)
    }

    @Test
    fun `toplam farkli urunler icin dogru hesaplanir`() {
        val viewModel = viewModel()

        viewModel.add(towel, towel.colorOptions.first())
        viewModel.add(shaker, null)
        viewModel.updateQuantity(viewModel.state.value.lines.last(), 3)

        assertEquals(449 + (699 * 3), viewModel.state.value.total)
        assertEquals(4, viewModel.state.value.itemCount)
    }
}
