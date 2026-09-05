package com.lmtoff.catalog.ui.cart

import androidx.lifecycle.ViewModel
import com.lmtoff.catalog.data.InMemoryProductRepository
import com.lmtoff.catalog.data.Product
import com.lmtoff.catalog.data.ProductColorOption
import com.lmtoff.catalog.data.ProductRepository
import com.lmtoff.catalog.data.priceValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Sepet durumunu tutar. Konfigürasyon değişikliklerinde (ör. ekran döndürme)
 * sepetin korunması için state Composable'ların dışında, ViewModel'de yaşar.
 */
class CartViewModel(
    private val repository: ProductRepository = InMemoryProductRepository()
) : ViewModel() {

    private val quantities = linkedMapOf<CartSelection, Int>()

    private val _state = MutableStateFlow(CartUiState())
    val state: StateFlow<CartUiState> = _state.asStateFlow()

    fun add(product: Product, colorOption: ProductColorOption?) {
        val key = CartSelection(product.id, colorOption?.label)
        quantities[key] = (quantities[key] ?: 0) + 1
        publish()
    }

    fun updateQuantity(line: CartLine, quantity: Int) {
        val key = CartSelection(line.product.id, line.colorOption?.label)
        if (quantity <= 0) quantities.remove(key) else quantities[key] = quantity
        publish()
    }

    fun clear() {
        quantities.clear()
        publish()
    }

    private fun publish() {
        val lines = quantities.mapNotNull { (selection, quantity) ->
            repository.productById(selection.productId)?.let { product ->
                CartLine(
                    product = product,
                    colorOption = product.colorOptions.firstOrNull { it.label == selection.colorLabel },
                    quantity = quantity
                )
            }
        }
        _state.value = CartUiState(
            lines = lines,
            itemCount = lines.sumOf { it.quantity },
            total = lines.sumOf { it.product.priceValue * it.quantity }
        )
    }
}
